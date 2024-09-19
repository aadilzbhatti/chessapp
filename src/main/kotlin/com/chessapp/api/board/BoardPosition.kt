package com.chessapp.api.board

import com.chessapp.BOARD_SIZE
import com.chessapp.api.pieces.piece.ChessPiece
import com.chessapp.api.pieces.piece.PieceColor
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The board position manager, used to manage
 * the positions of all the Chess pieces
 */
class BoardPosition {

    // The internal array used to keep track of the location of pieces
    private val positions: Array<Array<ChessPiece?>> = Array(BOARD_SIZE) { arrayOfNulls(BOARD_SIZE) }

    fun getPieceAtPosition(rank: File, file: Int): ChessPiece? {
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(rank, file)
        return positions[x][y]
    }

    fun isPositionOccupiedByPiece(rank: File, file: Int): Boolean {
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(rank, file)
        return positions[x][y] != null
    }

    fun occupyPosition(piece: ChessPiece) {
        val rank = piece.rank()
        val file = piece.file()
        occupyPosition(file, rank, piece)
    }

    fun movePiece(piece: ChessPiece, newFile: File, newRank: Int) {
        val gotPiece = getPieceAtPosition(piece.file(), piece.rank())
        if (gotPiece != piece) {
            throw InvalidPositionException("Piece internal coordinates do not line up with board position: $piece is has position ${piece.file()}${piece.rank()}, " +
                    "whereas piece at this position on the board is $gotPiece")
        }
        if (isPositionOccupiedByPiece(newFile, newRank)) {
            // TODO update this to handle captures etc
            throw InvalidPositionException("Square $newFile$newRank is already occupied by another piece")
        }

        removePiece(piece)
        occupyPosition(newFile, newRank, piece)
        piece.setPos(newFile, newRank)
    }

    private fun occupyPosition(file: File, rank: Int, piece: ChessPiece) {
        if (isPositionOccupiedByPiece(file, rank)) {
            throw InvalidPositionException("Square $file$rank is already occupied by another piece")
        }

        val (x, y) = PositionUtils.getCoordinatesFromFileRank(file, rank)
        positions[x][y] = piece
    }

    fun removePiece(piece: ChessPiece): ChessPiece? = removePiece(piece.file(), piece.rank())

    private fun removePiece(rank: File, file: Int): ChessPiece? {
        val (x, y) = PositionUtils.getCoordinatesFromFileRank(rank, file)
        return positions[x][y].also { positions[x][y] = null }
    }

    override fun toString(): String {
        val size = BOARD_SIZE
        val rotatedArray = Array(size) { Array<ChessPiece?>(size) { null } }

        // Rotating the array 90 degrees counterclockwise
        for (i in 0 until size) {
            for (j in 0 until size) {
                rotatedArray[size - j - 1][i] = positions[i][j]
            }
        }

        // Column labels (A to H)
        val columnLabels = ('A' until 'A' + size).joinToString(" ") { it.toString() }

        // Building the board with row numbers (8 to 1) and columns (A to H)
        return buildString {
            append("  ") // Leading spaces for row number alignment
            append(columnLabels)
            appendLine()

            for (i in 0 until size) {
                append(8 - i) // Row numbers (8 to 1)
                append(" ") // Space between row number and the row itself

                append(rotatedArray[i].joinToString(" ") { piece ->
                    piece?.let {
                        if (it.color() == PieceColor.WHITE) it.toString().uppercase() else it.toString().lowercase()
                    } ?: "."
                })

                append(" ") // Space between the row and the row number
                append(BOARD_SIZE - i) // Repeat row number at the end of the row
                appendLine()
            }

            append("  ") // Leading spaces for alignment
            append(columnLabels) // Bottom column labels
        }
    }
}
