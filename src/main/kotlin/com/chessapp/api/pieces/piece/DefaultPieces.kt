package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File

object DefaultPieces {
    // White pieces
    val WHITE_QUEENSIDE_ROOK = Rook(PieceColor.WHITE, File.A, 1)
    val WHITE_QUEENSIDE_KNIGHT = Knight(PieceColor.WHITE, File.B, 1)
    val WHITE_QUEENSIDE_BISHOP  = Bishop(PieceColor.WHITE, File.C, 1)
    val WHITE_QUEEN  = Queen(PieceColor.WHITE, File.D, 1)
    val WHITE_KING = King(PieceColor.WHITE, File.E, 1)
    val WHITE_KINGSIDE_BISHOP = Bishop(PieceColor.WHITE, File.F, 1)
    val WHITE_KINGSIDE_KNIGHT = Knight(PieceColor.WHITE, File.G, 1)
    val WHITE_KINGSIDE_ROOK = Rook(PieceColor.WHITE, File.H, 1)
    val WHITE_PAWNS: List<Pawn> = File.entries.map { Pawn(PieceColor.WHITE, it, 2) }

    val WHITE_PIECES = listOf(
        WHITE_QUEENSIDE_ROOK,
        WHITE_QUEENSIDE_KNIGHT,
        WHITE_QUEENSIDE_BISHOP,
        WHITE_QUEEN,
        WHITE_KING,
        WHITE_KINGSIDE_BISHOP,
        WHITE_KINGSIDE_KNIGHT,
        WHITE_KINGSIDE_ROOK
    ) + WHITE_PAWNS

    // Black pieces
    val BLACK_QUEENSIDE_ROOK = Rook(PieceColor.BLACK, File.A, 8)
    val BLACK_QUEENSIDE_KNIGHT = Knight(PieceColor.BLACK, File.B, 8)
    val BLACK_QUEENSIDE_BISHOP  = Bishop(PieceColor.BLACK, File.C, 8)
    val BLACK_QUEEN  = Queen(PieceColor.BLACK, File.D, 8)
    val BLACK_KING = King(PieceColor.BLACK, File.E, 8)
    val BLACK_KINGSIDE_BISHOP = Bishop(PieceColor.BLACK, File.F, 8)
    val BLACK_KINGSIDE_KNIGHT = Knight(PieceColor.BLACK, File.G, 8)
    val BLACK_KINGSIDE_ROOK = Rook(PieceColor.BLACK, File.H, 8)
    val BLACK_PAWNS = File.entries.map { Pawn(PieceColor.BLACK, it, 7) }

    val BLACK_PIECES = arrayOf(
        BLACK_QUEENSIDE_ROOK,
        BLACK_QUEENSIDE_KNIGHT,
        BLACK_QUEENSIDE_BISHOP,
        BLACK_QUEEN,
        BLACK_KING,
        BLACK_KINGSIDE_BISHOP,
        BLACK_KINGSIDE_KNIGHT,
        BLACK_KINGSIDE_ROOK
    ) + BLACK_PAWNS

    val ALL_PIECES = WHITE_PIECES + BLACK_PIECES
}