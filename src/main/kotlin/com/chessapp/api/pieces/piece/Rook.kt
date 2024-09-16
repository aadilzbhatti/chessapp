package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating Rook pieces
 */
class Rook(color: PieceColor, rank: Rank, file: Int) : ChessPiece(PieceName.ROOK, color, rank, file) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.rank != Rank.A && this.rank != Rank.H) {
            throw InvalidPositionException("Rooks must start on rank A or H, got: ${this.rank}")
        }
    }

    override fun toString() = "R"
}
