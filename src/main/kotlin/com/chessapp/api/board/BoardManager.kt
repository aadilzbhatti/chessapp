package com.chessapp.api.board

import com.chessapp.api.board.BoardPosition.occupyPosition
import com.chessapp.api.pieces.piece.Bishop
import com.chessapp.api.pieces.piece.King
import com.chessapp.api.pieces.piece.Knight
import com.chessapp.api.pieces.piece.Pawn
import com.chessapp.api.pieces.piece.Rook
import com.chessapp.api.pieces.piece.PieceColor
import com.chessapp.api.pieces.piece.Queen

object BoardManager {
    // White pieces
    private val whiteQueensideRook = Rook(PieceColor.WHITE, Rank.A, 1)
    private val whiteQueensideKnight = Knight(PieceColor.WHITE, Rank.B, 1)
    private val whiteQueensideBishop  = Bishop(PieceColor.WHITE, Rank.C, 1)
    private val whiteQueen  = Queen(PieceColor.WHITE, Rank.D, 1)
    private val whiteKing = King(PieceColor.WHITE, Rank.E, 1)
    private val whiteKingsideBishop = Bishop(PieceColor.WHITE, Rank.F, 1)
    private val whiteKingsideKnight = Knight(PieceColor.WHITE, Rank.G, 1)
    private val whiteKingsideRook = Rook(PieceColor.WHITE, Rank.H, 1)
    private val whitePawns: Array<Pawn> = emptyArray()

    // Black pieces
    private val blackQueensideRook = Rook(PieceColor.BLACK, Rank.A, 8)
    private val blackQueensideKnight = Knight(PieceColor.BLACK, Rank.B, 8)
    private val blackQueensideBishop  = Bishop(PieceColor.BLACK, Rank.C, 8)
    private val blackQueen  = Queen(PieceColor.BLACK, Rank.D, 8)
    private val blackKing = King(PieceColor.BLACK, Rank.E, 8)
    private val blackKingsideBishop = Bishop(PieceColor.BLACK, Rank.F, 8)
    private val blackKingsideKnight = Knight(PieceColor.BLACK, Rank.G, 8)
    private val blackKingsideRook = Rook(PieceColor.BLACK, Rank.H, 8)
    private val blackPawns: Array<Pawn> = emptyArray()


    init {
        Rank.entries.forEachIndexed { index, rank ->
            whitePawns[index] = Pawn(PieceColor.WHITE, rank, 2)
            blackPawns[index] = Pawn(PieceColor.BLACK, rank, 7)
        }

        occupyPosition(whiteQueensideRook)
        occupyPosition(whiteQueensideKnight)
        occupyPosition(whiteQueensideBishop)
        occupyPosition(whiteQueen)
        occupyPosition(whiteKing)
        occupyPosition(whiteKingsideBishop)
        occupyPosition(whiteKingsideKnight)
        occupyPosition(whiteKingsideRook)
        whitePawns.forEach { occupyPosition(it) }


        occupyPosition(blackQueensideRook)
        occupyPosition(blackQueensideKnight)
        occupyPosition(blackQueensideBishop)
        occupyPosition(blackQueen)
        occupyPosition(blackKing)
        occupyPosition(blackKingsideBishop)
        occupyPosition(blackKingsideKnight)
        occupyPosition(blackKingsideRook)
        blackPawns.forEach { occupyPosition(it) }
    }
}