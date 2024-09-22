package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File

/**
 * Template for pawn pieces
 */
class Pawn(color: PieceColor, file: File, rank: Int): ChessPiece(PieceName.PAWN, color, file, rank) {
    private var hasMovedOnce = false

    override fun validateStartingPosition() {
        PieceUtils.validatePawnStartingOnCorrectFile(this)
    }

    override fun toString() = "p"

    fun setHasMovedOnce() {
        hasMovedOnce = true
    }

    fun hasMovedOnce() = hasMovedOnce
}
