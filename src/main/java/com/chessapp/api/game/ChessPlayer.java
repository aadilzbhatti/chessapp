package com.chessapp.api.game;

import com.chessapp.api.board.BoardPosition;
import com.chessapp.api.pieces.piece.Bishop;
import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.piece.King;
import com.chessapp.api.pieces.piece.Knight;
import com.chessapp.api.pieces.piece.Pawn;
import com.chessapp.api.pieces.piece.Queen;
import com.chessapp.api.pieces.piece.Rook;
import com.chessapp.api.pieces.utils.ChessSide;

import java.util.ArrayList;

/**
 * The template for creating Chess players
 */

public class ChessPlayer {
    public Rook leftRook;
    public Rook rightRook;
    public Knight leftKnight;
    public Knight rightKnight;
    public Bishop leftBishop;
    public Bishop rightBishop;
    public Pawn[] pawns;
    public King king;
    public Queen queen;

    public ArrayList<ChessPiece> pieces;
    public ArrayList<ChessPiece> takenPieces;

    public PieceColor pieceColor;

    /**
     * Default way to create players. Initializes every required piece on
     * the chessboard.
     *
     * @param _color The color of the player (red, white)
     */
    public ChessPlayer(PieceColor _color) {
        pieceColor = _color;
        pieces = new ArrayList<>();
        takenPieces = new ArrayList<>();

        try {
            leftRook = new Rook(_color, ChessSide.LEFT);
            rightRook = new Rook(_color, ChessSide.RIGHT);
            pieces.add(leftRook);
            pieces.add(rightRook);

            leftKnight = new Knight(_color, ChessSide.LEFT);
            rightKnight = new Knight(_color, ChessSide.RIGHT);
            pieces.add(leftKnight);
            pieces.add(rightKnight);

            leftBishop = new Bishop(_color, ChessSide.LEFT, position);
            rightBishop = new Bishop(_color, ChessSide.RIGHT, position);
            pieces.add(leftBishop);
            pieces.add(rightBishop);

            pawns = new Pawn[8];
            for (int i = 0; i < pawns.length; i++) {
                pawns[i] = new Pawn(_color, position);
                pawns[i].setInitialPawnPosition(i, position);
                pieces.add(pawns[i]);
            }

            king = new King(_color, position);
            queen = new Queen(_color, position);
            pieces.add(king);
            pieces.add(queen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * How a player can attack another player and claim a piece from them
     *
     * @param piece    The attacking piece
     * @param newX     The new x-position (where the opposing piece is)
     * @param newY     The new y-position (where the opposing piece is)
     * @param position The board position manager
     * @param other    The other player whom we are attacking
     * @throws Exception thrown if position is invalid or some other management error
     */
    public void capture(ChessPiece piece, int newX, int newY, BoardPosition position, ChessPlayer other) throws Exception {
        try {
            ChessPiece takenPiece = piece.capture(newX, newY, position);
            other.pieces.remove(takenPiece);
            this.takenPieces.add(takenPiece);
        } catch (Exception e) {
            System.out.println(newX + ", " + newY);
            e.printStackTrace();
        }
    }
}
