package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import com.chessapp.api.pieces.utils.InvalidPositionException

/**
 * This is an example implementation of a ChessPiece, using
 * the Knight and its properties
 */
class Knight(color: PieceColor, rank: Rank, file: Int) : ChessPiece(PieceName.KNIGHT, color, rank, file) {
    override fun validateStartingPosition() {
        PieceUtils.validatePieceStartingOnCorrectFile(this)
        if (this.rank != Rank.B && this.rank != Rank.G) {
            throw InvalidPositionException("Knights must start on ranks B or G, got: ${this.rank}")
        }
    }
}
