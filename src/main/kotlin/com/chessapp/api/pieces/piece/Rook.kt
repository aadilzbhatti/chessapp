package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating Rook pieces
 */
class Rook(color: PieceColor, file: File, rank: Int) : ChessPiece(PieceName.ROOK, color, file, rank) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.file != File.A && this.file != File.H) {
            throw InvalidPositionException("Rooks must start on rank A or H, got: ${this.file}")
        }
    }

    override fun toString() = "R"
}
