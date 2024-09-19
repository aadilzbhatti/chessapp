package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating King pieces
 */
class King(color: PieceColor, file: File, rank: Int) : ChessPiece(PieceName.KING, color, file, rank) {
    override fun validateStartingPosition() {
       PieceUtils.validatePieceStartingOnCorrectFile(this)
       if (this.file != File.E) {
           throw InvalidPositionException("Kings must start on rank E, got: ${this.file}")
       }
    }

    override fun toString() = "K"
}
