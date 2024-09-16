package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank

/**
 * Template for pawn pieces
 */
class Pawn(color: PieceColor, rank: Rank, file: Int): ChessPiece(PieceName.PAWN, color, rank, file) {
    override fun validateStartingPosition() {
        PieceUtils.validatePawnStartingOnCorrectFile(this)
    }

    override fun toString() = "p"
}
