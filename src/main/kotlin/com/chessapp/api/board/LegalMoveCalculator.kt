package com.chessapp.api.board

import com.chessapp.BOARD_SIZE
import com.chessapp.api.pieces.piece.Bishop
import com.chessapp.api.pieces.piece.ChessPiece
import com.chessapp.api.pieces.piece.Knight
import com.chessapp.api.pieces.piece.PieceName
import com.chessapp.api.pieces.piece.Queen
import com.chessapp.api.pieces.piece.Rook

class LegalMoveCalculator(private val boardPosition: BoardPosition) {

    // TODO include more predicates for more complex conditions e.g. pins, checks, etc
    fun getLegalMovesForPiece(piece: ChessPiece): Set<Pair<File, Int>>? {
        return when (piece.name()) {
            PieceName.ROOK -> (piece as? Rook)?.let { computeLateralMoves(it) }
            PieceName.KNIGHT -> (piece as? Knight)?.let { computeLegalMovesForKnight(it) }
            PieceName.BISHOP ->  (piece as? Bishop)?.let { computeDiagonalMoves(it) }
            PieceName.QUEEN -> (piece as? Queen)?.let { computeMovesForQueen(it) }
            PieceName.KING -> TODO()
            PieceName.PAWN -> TODO()
        }
    }

    private fun computeLateralMoves(piece: ChessPiece): Set<Pair<File, Int>> {
        val (rank, file) = piece.run { rank() to file() }
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(file, rank)

        val xForwardMoves = (x + 1 until BOARD_SIZE).map { newX -> newX to y }.toMutableList()
        val xBackwardMoves = (x - 1 downTo 0).map { newX -> newX to y }.toMutableList()
        val yForwardMoves = (y + 1 until BOARD_SIZE).map { newY -> x to newY }.toMutableList()
        val yBackwardMoves = (y - 1 downTo 0).map { newY -> x to newY }.toMutableList()

        removeMovesAfterBlockingPieces(xForwardMoves)
        removeMovesAfterBlockingPieces(xBackwardMoves)
        removeMovesAfterBlockingPieces(yForwardMoves)
        removeMovesAfterBlockingPieces(yBackwardMoves)

        val possibleLegalMoves = xForwardMoves + xBackwardMoves + yForwardMoves + yBackwardMoves

        return getLegalMovesFromPossibleMoves(piece, possibleLegalMoves)
    }

    private fun computeLegalMovesForKnight(knight: Knight): Set<Pair<File, Int>> {
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

        return getLegalMovesFromPossibleMoves(knight, possibleLegalMoves)
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
        removeMovesAfterBlockingPieces(upperRightMoves)
        removeMovesAfterBlockingPieces(lowerRightMoves)
        removeMovesAfterBlockingPieces(upperLeftMoves)
        removeMovesAfterBlockingPieces(lowerLeftMoves)

        val possibleLegalMoves = upperRightMoves + lowerRightMoves + upperLeftMoves + lowerLeftMoves
        return getLegalMovesFromPossibleMoves(piece, possibleLegalMoves)
    }

    private fun computeMovesForQueen(queen: Queen): Set<Pair<File, Int>> =
        computeDiagonalMoves(queen) intersect computeLateralMoves(queen)

    private fun getLegalMovesFromPossibleMoves(piece: ChessPiece, possibleMoves: List<Pair<Int, Int>>): Set<Pair<File, Int>> {
        return possibleMoves.filter(PositionUtils::isValidCoordinates)
            .filter { (x, y) -> !sameColorPieceOccupiesPosition(piece, x to y) }
            .map { (x, y) -> PositionUtils.getFileRankFromCoordinates(x, y) }
            .toSet()
    }

    private fun sameColorPieceOccupiesPosition(piece: ChessPiece, position: Pair<Int, Int>): Boolean {
        val (rank, file) = PositionUtils.getFileRankFromCoordinates(position.first, position.second)
        return boardPosition.getPieceAtPosition(rank, file)?.color() == piece.color()
    }

    private fun removeMovesAfterBlockingPieces(moves: MutableList<Pair<Int, Int>>) {
        val index = moves.indexOfFirst { (newX, newY) ->
            val (ra, fi) = PositionUtils.getFileRankFromCoordinates(newX, newY)
            boardPosition.isPositionOccupiedByPiece(ra, fi)
        }
        if (index in (0..BOARD_SIZE)) {
            moves.subList(index, moves.size).clear()
        }
    }
}