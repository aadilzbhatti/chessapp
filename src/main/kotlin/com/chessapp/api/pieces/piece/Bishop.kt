package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating Bishop pieces
 */
class Bishop(color: PieceColor, rank: Rank, file: Int) : ChessPiece(PieceName.BISHOP, color, rank, file) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.rank != Rank.C && this.rank != Rank.F) {
            throw InvalidPositionException("Bishops must start on ranks C or F, got: ${this.rank}")
        }
    }
}
