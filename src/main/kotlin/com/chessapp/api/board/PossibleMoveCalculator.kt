package com.chessapp.api.board

import com.chessapp.BOARD_SIZE
import com.chessapp.api.pieces.piece.Bishop
import com.chessapp.api.pieces.piece.ChessPiece
import com.chessapp.api.pieces.piece.King
import com.chessapp.api.pieces.piece.Knight
import com.chessapp.api.pieces.piece.Pawn
import com.chessapp.api.pieces.piece.PieceColor
import com.chessapp.api.pieces.piece.PieceName
import com.chessapp.api.pieces.piece.Queen
import com.chessapp.api.pieces.piece.Rook

/**
 * PossibleMoveCalculator computes possible moves, i.e. the potential valid moves that each piece can make,
 * without assumption of any game conditions such as pins or checks
 */
class PossibleMoveCalculator(private val boardPosition: BoardPosition) {

    fun getPossibleMovesForPiece(piece: ChessPiece): Set<Pair<File, Int>>? =
        when (piece.name()) {
            PieceName.ROOK -> (piece as? Rook)?.let { computeLateralMoves(it) }
            PieceName.KNIGHT -> (piece as? Knight)?.let { computeMovesForKnight(it) }
            PieceName.BISHOP ->  (piece as? Bishop)?.let { computeDiagonalMoves(it) }
            PieceName.QUEEN -> (piece as? Queen)?.let { computeMovesForQueen(it) }
            PieceName.KING -> (piece as? King)?.let { computeMovesForKing(it) }
            PieceName.PAWN -> (piece as? Pawn)?.let { computeMovesForPawn(it) }
        }

    private fun computeLateralMoves(piece: ChessPiece): Set<Pair<File, Int>> {
        val (rank, file) = piece.run { rank() to file() }
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(file, rank)

        val xForwardMoves = (x + 1 until BOARD_SIZE).map { newX -> newX to y }.toMutableList()
        val xBackwardMoves = (x - 1 downTo 0).map { newX -> newX to y }.toMutableList()
        val yForwardMoves = (y + 1 until BOARD_SIZE).map { newY -> x to newY }.toMutableList()
        val yBackwardMoves = (y - 1 downTo 0).map { newY -> x to newY }.toMutableList()

        removeMovesAfterBlockingPieces(piece, xForwardMoves)
        removeMovesAfterBlockingPieces(piece, xBackwardMoves)
        removeMovesAfterBlockingPieces(piece, yForwardMoves)
        removeMovesAfterBlockingPieces(piece, yBackwardMoves)

        val possibleLegalMoves = xForwardMoves + xBackwardMoves + yForwardMoves + yBackwardMoves

        return getValidMovesFromPossibleMoves(piece, possibleLegalMoves)
    }

    private fun computeMovesForKnight(knight: Knight): Set<Pair<File, Int>> {
        val (rank, file) = knight.run { rank() to file() }
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(file, rank)
        val possibleLegalMoves = listOf(
            x + 2 to y + 1,
            x + 2 to y - 1,
            x - 2 to y + 1,
            x - 2 to y - 1,
            x + 1 to y + 2,
            x + 1 to y - 2,
            x - 1 to y + 2,
            x - 1 to y - 2,
        )

        return getValidMovesFromPossibleMoves(knight, possibleLegalMoves)
    }

    private fun computeDiagonalMoves(piece: ChessPiece): Set<Pair<File, Int>> {
        val (rank, file) = piece.run { rank() to file() }
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(file, rank)

        fun generateDiagonalMoves(startX: Int, startY: Int, stepX: Int, stepY: Int): Sequence<Pair<Int, Int>> = sequence {
            var currX = startX + stepX
            var currY = startY + stepY
            while (PositionUtils.isValidCoordinates(currX to currY)) {
                yield(currX to currY) // Add the move
                currX += stepX
                currY += stepY
            }
        }

        // Get diagonal moves in all 4 directions
        val upperRightMoves = generateDiagonalMoves(x, y, 1, 1).toMutableList()
        val lowerRightMoves = generateDiagonalMoves(x, y, 1, -1).toMutableList()
        val upperLeftMoves = generateDiagonalMoves(x, y, -1, 1).toMutableList()
        val lowerLeftMoves = generateDiagonalMoves(x, y, -1, -1).toMutableList()

        // Remove moves after encountering blocking pieces
        removeMovesAfterBlockingPieces(piece, upperRightMoves)
        removeMovesAfterBlockingPieces(piece, lowerRightMoves)
        removeMovesAfterBlockingPieces(piece, upperLeftMoves)
        removeMovesAfterBlockingPieces(piece, lowerLeftMoves)

        val possibleLegalMoves = upperRightMoves + lowerRightMoves + upperLeftMoves + lowerLeftMoves
        return getValidMovesFromPossibleMoves(piece, possibleLegalMoves)
    }

    private fun computeMovesForQueen(queen: Queen): Set<Pair<File, Int>> =
        computeDiagonalMoves(queen) union computeLateralMoves(queen)

    private fun computeMovesForKing(king: King): Set<Pair<File, Int>> {
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(king.file(), king.rank())

        val possibleMoves = listOf(
            x + 1 to y,
            x + 1 to y + 1,
            x + 1 to y - 1,
            x - 1 to y,
            x - 1 to y + 1,
            x - 1 to y - 1,
            x to y + 1,
            x to y - 1,
        )

        return getValidMovesFromPossibleMoves(king, possibleMoves)
    }

    private fun computeMovesForPawn(pawn: Pawn): Set<Pair<File, Int>> {
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(pawn.file(), pawn.rank())

        val possibleMoves = mutableListOf<Pair<Int, Int>>().apply {
            when (pawn.color()) {
                PieceColor.WHITE -> {
                    add(x to y + 1)
                    getPossiblePawnCaptureMove(x - 1, y + 1, pawn)?.let(::add)
                    getPossiblePawnCaptureMove(x + 1, y + 1, pawn)?.let(::add)
                    if (!pawn.hasMovedOnce()) add(x to y + 2)
                }
                PieceColor.BLACK -> {
                    add(x to y - 1)
                    getPossiblePawnCaptureMove(x - 1, y - 1, pawn)?.let(::add)
                    getPossiblePawnCaptureMove(x + 1, y - 1, pawn)?.let(::add)
                    if (!pawn.hasMovedOnce()) add(x to y - 2)
                }
            }
        }

        return getValidMovesFromPossibleMoves(pawn, possibleMoves)
    }

    private fun getPossiblePawnCaptureMove(x: Int, y: Int, pawn: Pawn): Pair<Int, Int>? {
        return if (PositionUtils.isValidCoordinates(x to y)) {
            val (file, rank) = PositionUtils.getFileRankFromCoordinates(x, y)
            boardPosition.getPieceAtPosition(file, rank)?.takeIf { it.color() != pawn.color() }?.run {
                x to y
            }
        } else null
    }

    private fun getValidMovesFromPossibleMoves(piece: ChessPiece, possibleMoves: List<Pair<Int, Int>>): Set<Pair<File, Int>> {
        return possibleMoves.filter(PositionUtils::isValidCoordinates)
            .filter { (x, y) -> !sameColorPieceOccupiesPosition(piece, x to y) }
            .map { (x, y) -> PositionUtils.getFileRankFromCoordinates(x, y) }
            .toSet()
    }

    private fun sameColorPieceOccupiesPosition(piece: ChessPiece, position: Pair<Int, Int>): Boolean {
        val (rank, file) = PositionUtils.getFileRankFromCoordinates(position.first, position.second)
        return boardPosition.getPieceAtPosition(rank, file)?.color() == piece.color()
    }

    private fun removeMovesAfterBlockingPieces(piece: ChessPiece, moves: MutableList<Pair<Int, Int>>) {
        var blockingPiece: ChessPiece? = null
        var index = moves.indexOfFirst { (newX, newY) ->
            val (file, rank) = PositionUtils.getFileRankFromCoordinates(newX, newY)
            val pieceAtPosition = boardPosition.getPieceAtPosition(file, rank)
            if (pieceAtPosition != null) {
                blockingPiece = pieceAtPosition
                true
            } else false
        }
        if (blockingPiece?.let { it.color() != piece.color() } == true) {
           index++
        }
        if (index in (0 until BOARD_SIZE)) {
            moves.subList(index, moves.size).clear()
        }
    }
}