package com.chessapp.api.pieces.piece;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;
import com.chessapp.api.pieces.utils.ChessSide;

/**
 * This is an example implementation of a ChessPiece, using
 * the Knight and its properties
 */
public class Knight extends ChessPiece {

    /**
     * Constructor used to create Knight pieces
     *
     * @param color the color of the Knight
     * @throws Exception if an invalid side is given
     */
    public Knight(ChessPieceColor color, ChessSide side, BoardPosition position) throws Exception {
        super(ChessPieceName.KNIGHT, color, side, position);

        int size = position.getSize();
        if (color == ChessPieceColor.WHITE) {
            Utils.setPosWithSide(this, 1, size - 1, size - 2, size - 1);
        } else {
            Utils.setPosWithSide(this, 1, 0, size - 2, 0);
        }
        position.occupyPosition(xPos, yPos, this);
    }

    /**
     * The implemented move function, used to move the Knight after
     * checking its bounds and final position for validity
     *
     * @param newX - the new x-position of the piece, checked for validity
     * @param newY - the new y-position of the piece, also checked
     * @throws Exception if an invalid position is given
     */
    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove((changeX != 2 && changeY != 1) || (changeX != 1 && changeY != 2), this, newX, newY, position);
        Utils.makeValidMove(this, newX, newY, position);
    }

    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove((changeX != 2 && changeY != 1) || (changeX != 1 && changeY != 2), this, newX, newY, position);
        return Utils.attackOrMove(this, newX, newY, position);
    }
}
