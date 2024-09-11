package com.chessapp.api.pieces.piece;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;

/**
 * The template for creating King pieces
 */

public class King extends ChessPiece {
    public King(ChessPieceColor color, BoardPosition position) throws Exception {
        super(ChessPieceName.KING, color, position);

        int size = position.getSize();
        if (color == ChessPieceColor.WHITE) {
            this.setPos(4, size - 1);
        } else {
            this.setPos(4, 0);
        }
        position.occupyPosition(xPos, yPos, this);
    }

    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove((changeX == 1 && changeY == 1)
                || (changeX == 1 && changeY == 0)
                || (changeX == 0 && changeY == 1), this, newX, newY, position);
        Utils.makeValidMove(this, newX, newY, position);
    }

    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove((changeX == 1 && changeY == 1)
                || (changeX == 1 && changeY == 0)
                || (changeX == 0 && changeY == 1), this, newX, newY, position);
        return Utils.attackOrMove(this, newX, newY, position);
    }
}
