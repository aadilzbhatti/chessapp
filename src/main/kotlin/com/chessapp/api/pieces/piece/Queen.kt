package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating Queen pieces
 */
class Queen(color: PieceColor, rank: Rank, file: Int) : ChessPiece(PieceName.QUEEN, color, rank, file) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.rank != Rank.D) {
            throw InvalidPositionException("Queens must start on rank D, got: ${this.rank}")
        }
    }
}

