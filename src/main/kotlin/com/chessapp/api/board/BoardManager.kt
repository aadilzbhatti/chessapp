package com.chessapp.api.board

import com.chessapp.api.pieces.piece.Bishop
import com.chessapp.api.pieces.piece.King
import com.chessapp.api.pieces.piece.Knight
import com.chessapp.api.pieces.piece.Pawn
import com.chessapp.api.pieces.piece.PieceColor
import com.chessapp.api.pieces.piece.Queen
import com.chessapp.api.pieces.piece.Rook

object BoardManager {
    private lateinit var position: BoardPosition

    // White pieces
    private val whiteQueensideRook = Rook(PieceColor.WHITE, File.A, 1)
    private val whiteQueensideKnight = Knight(PieceColor.WHITE, File.B, 1)
    private val whiteQueensideBishop  = Bishop(PieceColor.WHITE, File.C, 1)
    private val whiteQueen  = Queen(PieceColor.WHITE, File.D, 1)
    private val whiteKing = King(PieceColor.WHITE, File.E, 1)
    private val whiteKingsideBishop = Bishop(PieceColor.WHITE, File.F, 1)
    private val whiteKingsideKnight = Knight(PieceColor.WHITE, File.G, 1)
    private val whiteKingsideRook = Rook(PieceColor.WHITE, File.H, 1)
    private val whitePawns: List<Pawn> = File.entries.map { Pawn(PieceColor.WHITE, it, 2) }

    private val whitePieces = listOf(
        whiteQueensideRook,
        whiteQueensideKnight,
        whiteQueensideBishop,
        whiteQueen,
        whiteKing,
        whiteKingsideBishop,
        whiteKingsideKnight,
        whiteKingsideRook
    ) + whitePawns

    // Black pieces
    private val blackQueensideRook = Rook(PieceColor.BLACK, File.A, 8)
    private val blackQueensideKnight = Knight(PieceColor.BLACK, File.B, 8)
    private val blackQueensideBishop  = Bishop(PieceColor.BLACK, File.C, 8)
    private val blackQueen  = Queen(PieceColor.BLACK, File.D, 8)
    private val blackKing = King(PieceColor.BLACK, File.E, 8)
    private val blackKingsideBishop = Bishop(PieceColor.BLACK, File.F, 8)
    private val blackKingsideKnight = Knight(PieceColor.BLACK, File.G, 8)
    private val blackKingsideRook = Rook(PieceColor.BLACK, File.H, 8)
    private val blackPawns = File.entries.map { Pawn(PieceColor.BLACK, it, 7) }

    private val blackPieces = arrayOf(
        blackQueensideRook,
        blackQueensideKnight,
        blackQueensideBishop,
        blackQueen,
        blackKing,
        blackKingsideBishop,
        blackKingsideKnight,
        blackKingsideRook
    ) + blackPawns

    private val allPieces = whitePieces + blackPieces

    fun allPieces() = allPieces

    fun boardPosition() = position

    init {
      initialize()
    }

    fun initialize() {
        position = BoardPosition()

        position.occupyPosition(whiteQueensideRook)
        position.occupyPosition(whiteQueensideKnight)
        position.occupyPosition(whiteQueensideBishop)
        position.occupyPosition(whiteQueen)
        position.occupyPosition(whiteKing)
        position.occupyPosition(whiteKingsideBishop)
        position.occupyPosition(whiteKingsideKnight)
        position.occupyPosition(whiteKingsideRook)
        whitePawns.forEach { position.occupyPosition(it) }

        position.occupyPosition(blackQueensideRook)
        position.occupyPosition(blackQueensideKnight)
        position.occupyPosition(blackQueensideBishop)
        position.occupyPosition(blackQueen)
        position.occupyPosition(blackKing)
        position.occupyPosition(blackKingsideBishop)
        position.occupyPosition(blackKingsideKnight)
        position.occupyPosition(blackKingsideRook)
        blackPawns.forEach { position.occupyPosition(it) }
    }
}