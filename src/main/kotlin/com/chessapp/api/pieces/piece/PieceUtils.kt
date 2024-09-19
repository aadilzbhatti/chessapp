package com.chessapp.api.pieces.piece

import com.chessapp.api.pieces.utils.InvalidPositionException

object PieceUtils {
    fun validatePawnStartingOnCorrectFile(piece: ChessPiece) {
        when(piece.color()) {
            PieceColor.BLACK -> {
                if (piece.rank() != 7) {
                    throw InvalidPositionException("Black pawns must start on file 7, got: ${piece.file()}")
                }
            }
            PieceColor.WHITE -> {
                if (piece.rank() != 2) {
                    throw InvalidPositionException("White pawns must start on file 2, got: ${piece.file()}")
                }
            }
        }
    }
    fun validatePieceStartingOnCorrectFile(piece: ChessPiece) {
        when(piece.color()) {
            PieceColor.BLACK -> {
                if (piece.rank() != 8) {
                    throw InvalidPositionException("Black  ${piece.getFormattedName(false)}s must start on file 8, got: ${piece.file()}")
                }
            }
            PieceColor.WHITE -> {
                if (piece.rank() != 1) {
                    throw InvalidPositionException("White ${piece.getFormattedName(false)}s must start on file 1, got: ${piece.file()}")
                }
            }
        }
    }
}

enum class PieceColor {
    BLACK,
    WHITE
}

enum class PieceName {
    KNIGHT,
    ROOK,
    PAWN,
    KING,
    QUEEN,
    BISHOP,
}