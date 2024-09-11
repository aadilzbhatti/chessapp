package com.chessapp.api.pieces.piece;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;
import com.chessapp.api.pieces.utils.ChessSide;

/**
 * The template for creating Bishop pieces
 */

public class Bishop extends ChessPiece {
    /**
     * The constructor for creating positioned & colored Bishop pieces
     * @param color The desired color of the Bishop
     * @param side The desired side of the Bishop (left, right)
     * @param position The board position manager
     * @throws Exception thrown if invalid piece given
     */
    public Bishop(ChessPieceColor color, ChessSide side, BoardPosition position) throws Exception {
        super(ChessPieceName.BISHOP, color, side, position);

        int size = position.getSize();
        if (color == ChessPieceColor.WHITE) {
            Utils.setPosWithSide(this, 2, size - 1, size - 3, size - 1);
        } else {
            Utils.setPosWithSide(this, 2, 0, size - 3, 0);
        }
        position.occupyPosition(xPos, yPos, this);
    }

    /**
     * Allows Bishops to move in diagonal directions
     * @param newX - the new x-position of the piece, checked for validity
     * @param newY - the new y-position of the piece, also checked
     * @param position The board position manager
     * @throws Exception thrown if invalid position given
     */
    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove(changeX == changeY, this, newX, newY, position);
        Utils.makeValidMove(this, newX, newY, position);
    }

    /**
     * Allows Bishops to attack a particular position
     * @param newX The new x-position of the piece
     * @param newY The new y-position of the piece
     * @param position the board position manager
     * @throws Exception if invalid position given
     * @return The newly taken chess piece
     */
    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove(changeX == changeY, this, newX, newY, position);
        return Utils.attackOrMove(this, newX, newY, position);
    }
}
