package com.chessapp.api.pieces.piece;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;

/**
 * The template for creating Queen pieces
 */

public class Queen extends ChessPiece {

    /**
     * Default constructor to build Queen pieces
     *
     * @param color    The color of the Queen
     * @param position The board position manager
     * @throws Exception Thrown if position given is invalid or side / color invalid
     */
    public Queen(ChessPieceColor color, BoardPosition position) throws Exception {
        super(ChessPieceName.QUEEN, color, position);

        int size = position.getSize();
        if (color == ChessPieceColor.WHITE) {
            this.setPos(3, size - 1);
        } else {
            this.setPos(3, 0);
        }
        position.occupyPosition(xPos, yPos, this);
    }

    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - xPos);
        int changeY = Math.abs(newY - yPos);

        Utils.checkValidMove((changeX == changeY)
                || (changeX > 0 && changeY == 0)
                || (changeX == 0 && changeY > 0), this, newX, newY, position);

        Utils.makeValidMove(this, newX, newY, position);
    }

    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove((changeX == changeY)
                || (changeX > 0 && changeY == 0)
                || (changeX == 0 && changeY > 0), this, newX, newY, position);

        return Utils.attackOrMove(this, newX, newY, position);
    }
}

