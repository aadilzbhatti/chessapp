package com.chessapp.api.board

import com.chessapp.BOARD_SIZE
import com.chessapp.api.pieces.piece.ChessPiece
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The board position manager, used to manage
 * the positions of all the Chess pieces
 */
object BoardPosition {

    // The internal array used to keep track of the location of pieces
    private val positions: Array<Array<ChessPiece?>> = Array(BOARD_SIZE) { arrayOfNulls(BOARD_SIZE) }

    fun getPieceAtPosition(rank: Rank, file: Int): ChessPiece? {
        val (x, y) = PositionUtils.getCoordinatesFromRankFile(rank, file)
        return positions[x][y]
    }

    fun isPositionOccupiedByPiece(rank: Rank, file: Int): Boolean {
        val (x, y) = PositionUtils.getCoordinatesFromRankFile(rank, file)
        return positions[x][y] != null
    }

    fun occupyPosition(piece: ChessPiece) {
        val rank = piece.rank()
        val file = piece.file()
        occupyPosition(rank, file, piece)
    }

    private fun occupyPosition(rank: Rank, file: Int, piece: ChessPiece) {
        if (isPositionOccupiedByPiece(rank, file)) {
            throw InvalidPositionException("Square $rank$file is already occupied by another piece")
        }

        val (x, y) = PositionUtils.getCoordinatesFromRankFile(rank, file)
        positions[x][y] = piece
    }

    fun removePiece(piece: ChessPiece): ChessPiece? = removePiece(piece.rank(), piece.file())

    private fun removePiece(rank: Rank, file: Int): ChessPiece? {
        val (x, y) = PositionUtils.getCoordinatesFromRankFile(rank, file)
        return positions[x][y].also { positions[x][y] = null }
    }

    override fun toString(): String {
        val size = positions.size
        val rotatedArray = Array(size) { Array<ChessPiece?>(size) { null } }

        for (i in 0 until size) {
            for (j in 0 until size) {
                rotatedArray[size - j - 1][i] = positions[i][j]
            }
        }

        return rotatedArray.joinToString(" |\n") { row ->
            row.joinToString(" -- ") { piece ->
                piece?.toString() ?: "."
            }
        }
    }
}
