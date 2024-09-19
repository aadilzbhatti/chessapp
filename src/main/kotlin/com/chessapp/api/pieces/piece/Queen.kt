package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * The template for creating Queen pieces
 */
class Queen(color: PieceColor, file: File, rank: Int) : ChessPiece(PieceName.QUEEN, color, file, rank) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.file != File.D) {
            throw InvalidPositionException("Queens must start on rank D, got: ${this.file}")
        }
    }

    override fun toString() = "Q"
}

