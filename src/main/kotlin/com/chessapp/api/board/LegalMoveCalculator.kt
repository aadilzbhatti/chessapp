package com.chessapp.api.board

import com.chessapp.BOARD_SIZE
import com.chessapp.api.pieces.piece.ChessPiece
import com.chessapp.api.pieces.piece.Knight
import com.chessapp.api.pieces.piece.PieceName
import com.chessapp.api.pieces.piece.Rook

object LegalMoveCalculator {

    // TODO include more predicates for more complex conditions e.g. pins, checks, etc
    fun getLegalMovesForPiece(piece: ChessPiece): Set<Pair<Rank, Int>>? {
        return when (piece.name()) {
            PieceName.KNIGHT -> (piece as? Knight)?.let { computeLegalMovesForKnight(it) }
            PieceName.ROOK -> (piece as? Rook)?.let { computeLegalMovesForRook(it) }
            PieceName.PAWN -> TODO()
            PieceName.KING -> TODO()
            PieceName.QUEEN -> TODO()
            PieceName.BISHOP -> TODO()
        }
    }

    private fun computeLegalMovesForKnight(knight: Knight): Set<Pair<Rank, Int>> {
        val (rank, file) = knight.run { rank() to file() }
        val (x, y) = PositionUtils.getCoordinatesFromRankFile(rank, file)
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

    private fun computeLegalMovesForRook(rook: Rook): Set<Pair<Rank, Int>> {
        fun removeMovesAfterBlockingPieces(moves: MutableList<Pair<Int, Int>>) {
            val blockCondition: (Rank, Int) -> Boolean = { ra: Rank, fi: Int -> BoardPosition.isPositionOccupiedByPiece(ra, fi)}
            val index = moves.indexOfFirst { (newX, newY) ->
                val (ra, fi) = PositionUtils.getRankFileFromCoordinates(newX, newY)
                blockCondition(ra, fi)
            }
            if (index in (0..BOARD_SIZE)) {
                moves.subList(index, moves.size).clear()
            }
        }

        val (rank, file) = rook.run { rank() to file() }
        val (x, y) = PositionUtils.getCoordinatesFromRankFile(rank, file)

        val xForwardMoves = (x + 1 until BOARD_SIZE).map { newX -> newX to y }.toMutableList()
        val xBackwardMoves = (0 until x).map { newX -> newX to y }.toMutableList()
        val yForwardMoves = (y + 1 until BOARD_SIZE).map { newY -> x to newY }.toMutableList()
        val yBackwardMoves = (0 until y).map { newY -> x to newY }.toMutableList()

        removeMovesAfterBlockingPieces(xForwardMoves)
        removeMovesAfterBlockingPieces(xBackwardMoves)
        removeMovesAfterBlockingPieces(yForwardMoves)
        removeMovesAfterBlockingPieces(yBackwardMoves)

        val possibleLegalMoves = (xForwardMoves + xBackwardMoves + yForwardMoves + yBackwardMoves)

        return getLegalMovesFromPossibleMoves(rook, possibleLegalMoves)
    }

    private fun getLegalMovesFromPossibleMoves(piece: ChessPiece, possibleMoves: List<Pair<Int, Int>>): Set<Pair<Rank, Int>> {
        return possibleMoves.filter(PositionUtils::validCoordinatesFilter)
            .filter { (x, y) -> !sameColorPieceOccupiesPosition(piece, x to y) }
            .map { (x, y) -> PositionUtils.getRankFileFromCoordinates(x, y) }
            .toSet()
    }

    private fun sameColorPieceOccupiesPosition(piece: ChessPiece, position: Pair<Int, Int>): Boolean {
        val (rank, file) = PositionUtils.getRankFileFromCoordinates(position.first, position.second)
        return BoardPosition.getPieceAtPosition(rank, file)?.color() == piece.color()
    }
}