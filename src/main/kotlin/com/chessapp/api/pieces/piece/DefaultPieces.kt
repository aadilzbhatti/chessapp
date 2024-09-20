package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File

object DefaultPieces {
    // White pieces
    fun whiteQueensideRook() = Rook(PieceColor.WHITE, File.A, 1)
    fun whiteQueensideKnight() = Knight(PieceColor.WHITE, File.B, 1)
    fun whiteQueensideBishop()  = Bishop(PieceColor.WHITE, File.C, 1)
    fun whiteQueen()  = Queen(PieceColor.WHITE, File.D, 1)
    fun whiteKing() = King(PieceColor.WHITE, File.E, 1)
    fun whiteKingsideBishop() = Bishop(PieceColor.WHITE, File.F, 1)
    fun whiteKingsideKnight() = Knight(PieceColor.WHITE, File.G, 1)
    fun whiteKingsideRook() = Rook(PieceColor.WHITE, File.H, 1)
    fun whitePawns(): List<Pawn> = File.entries.map { Pawn(PieceColor.WHITE, it, 2) }

    fun whitePieces() = listOf(
        whiteQueensideRook(),
        whiteQueensideKnight(),
        whiteQueensideBishop(),
        whiteQueen(),
        whiteKing(),
        whiteKingsideBishop(),
        whiteKingsideKnight(),
        whiteKingsideRook()
    ) + whitePawns()

    // Black pieces
    fun blackQueensideRook() = Rook(PieceColor.BLACK, File.A, 8)
    fun blackQueensideKnight() = Knight(PieceColor.BLACK, File.B, 8)
    fun blackQueensideBishop()  = Bishop(PieceColor.BLACK, File.C, 8)
    fun blackQueen()  = Queen(PieceColor.BLACK, File.D, 8)
    fun blackKing() = King(PieceColor.BLACK, File.E, 8)
    fun blackKingsideBishop() = Bishop(PieceColor.BLACK, File.F, 8)
    fun blackKingsideKnight() = Knight(PieceColor.BLACK, File.G, 8)
    fun blackKingsideRook() = Rook(PieceColor.BLACK, File.H, 8)
    fun blackPawns() = File.entries.map { Pawn(PieceColor.BLACK, it, 7) }

    fun blackPieces() = listOf(
        blackQueensideRook(),
        blackQueensideKnight(),
        blackQueensideBishop(),
        blackQueen(),
        blackKing(),
        blackKingsideBishop(),
        blackKingsideKnight(),
        blackKingsideRook()
    ) + blackPawns()

    fun allPieces() = whitePieces() + blackPieces()
}