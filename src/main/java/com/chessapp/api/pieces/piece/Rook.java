package com.chessapp.api.pieces.piece;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;
import com.chessapp.api.pieces.utils.ChessSide;

/**
 * The template for creating Rook pieces
 */

public class Rook extends ChessPiece {
    public Rook(ChessPieceColor color, ChessSide side, BoardPosition position) throws Exception {
        super(ChessPieceName.ROOK, color, side, position);

        int size = position.getSize();
        if (color == ChessPieceColor.WHITE) {
            Utils.setPosWithSide(this, 0, size - 1, size - 1, size - 1);
        } else {
            Utils.setPosWithSide(this, 0, 0, size - 1, 0);
        }
        position.occupyPosition(xPos, yPos, this);
    }

    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove(changeX > 0 && changeY == 0 || changeX == 0 && changeY > 0, this, newX, newY, position);
        Utils.makeValidMove(this, newX, newY, position);
    }

    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove(changeX > 0 && changeY == 0 || changeX == 0 && changeY > 0, this, newX, newY, position);
        return Utils.attackOrMove(this, newX, newY, position);
    }
}
