package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating Bishop pieces
 */
class Bishop(color: PieceColor, file: File, rank: Int) : ChessPiece(PieceName.BISHOP, color, file, rank) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.file != File.C && this.file != File.F) {
            throw InvalidPositionException("Bishops must start on ranks C or F, got: ${this.file}")
        }
    }

    override fun toString() = "B"
}
