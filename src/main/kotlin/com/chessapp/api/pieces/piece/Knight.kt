package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * This is an example implementation of a ChessPiece, using
 * the Knight and its properties
 */
class Knight(color: PieceColor, file: File, rank: Int) : ChessPiece(PieceName.KNIGHT, color, file, rank) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.file != File.B && this.file != File.G) {
            throw InvalidPositionException("Knights must start on ranks B or G, got: ${this.file}")
        }
    }

    override fun toString() = "N"
}
