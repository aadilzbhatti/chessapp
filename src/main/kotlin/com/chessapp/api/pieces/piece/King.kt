package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating King pieces
 */
class King(color: PieceColor, rank: Rank, file: Int) : ChessPiece(PieceName.KING, color, rank, file) {
    override fun validateStartingPosition() {
       PieceUtils.validatePieceStartingOnCorrectFile(this)
       if (this.rank != Rank.E) {
           throw InvalidPositionException("Kings must start on rank E, got: ${this.rank}")
       }
    }

    override fun toString() = "K"
}
