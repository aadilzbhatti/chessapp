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
        val (xPos, yPos) = getBoardCoordinates(rank, file)
        return positions[xPos][yPos]
    }

    private fun isPositionOccupied(rank: Rank, file: Int): Boolean {
        val (xPos, yPos) = getBoardCoordinates(rank, file)
        return positions[xPos][yPos] != null
    }

    fun occupyPosition(piece: ChessPiece) {
        val rank = piece.rank()
        val file = piece.file()
        occupyPosition(rank, file, piece)
    }

    fun occupyPosition(rank: Rank, file: Int, piece: ChessPiece) {
        if (isPositionOccupied(rank, file)) {
            throw InvalidPositionException("Square $rank$file is occupied by another piece")
        }

        val (xPos, yPos) = getBoardCoordinates(rank, file)
        positions[xPos][yPos] = piece
    }

    fun removePiece(rank: Rank, file: Int): ChessPiece? {
        val (xPos, yPos) = getBoardCoordinates(rank, file)
        return positions[xPos][yPos].also { positions[xPos][yPos] = null }
    }

    private fun getBoardCoordinates(rank: Rank, file: Int): Pair<Int, Int> {
        validateFile(file)
        val xPos = when (rank) {
            Rank.A -> 0
            Rank.B -> 1
            Rank.C -> 2
            Rank.D -> 3
            Rank.E -> 4
            Rank.F -> 5
            Rank.G -> 6
            Rank.H -> 7
        }
        val yPos = file - 1
        return Pair(xPos, yPos)
    }

    private fun validateFile(file: Int) {
        if (file < 1 || file > 8) {
            throw InvalidPositionException("Invalid file with value $file: must be between 1 and 8")
        }
    }
}

enum class Rank {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H
}
