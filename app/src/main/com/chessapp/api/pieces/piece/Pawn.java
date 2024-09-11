package com.chessapp.api.pieces.piece;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.utils.ChessPieceColor;

/**
 * Template for pawn pieces
 */
public class Pawn extends ChessPiece {
    boolean hasMoved = false;
    public int offset;

    /**
     * Default constructor for pawns. Offset
     * used to determine y-position
     *
     * @param _color the color of the pawn
     */
    public Pawn(ChessPieceColor _color, BoardPosition position) {
        super(_color, position);
        int size = position.getSize();
        offset = _color == ChessPieceColor.WHITE ? size - 2 : 1;
    }

    /**
     * Setting the initial pawn position is a bit different
     * since the y-position is fixed based on the color, so
     * we only really change the x-position
     *
     * @param initialX The initial x-position of the pawn
     * @param position The board position manager
     */
    public void setInitialPawnPosition(int initialX, BoardPosition position) throws Exception {
        xPos = initialX;
        yPos = offset;
        position.occupyPosition(xPos, yPos, this);
    }

    /**
     * Moving for pawns is different since at first they can move up to 2 spaces
     *
     * @param newX - the new x-position of the piece, checked for validity
     * @param newY - the new y-position of the piece, also checked
     * @param position The board position manager
     * @throws Exception if an invalid final position is given
     */
    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        if (hasMoved) {
            Utils.checkValidMove(changeX == 0 && changeY == 1, this, newX, newY, position);
        } else {
            Utils.checkValidMove((changeX == 0 && changeY == 2)
                || (changeX == 0 && changeY == 1), this, newX, newY, position);
            hasMoved = true;
        }
        Utils.makeValidMove(this, newX, newY, position);
    }

    /**
     * Attacking for Pawns is a bit different, since they can only attack diagonally.
     *
     * @param newX The new x-position of the piece
     * @param newY The new y-position of the piece
     * @param position the board position manager
     * @throws Exception if an invalid final position is given or invalid move
     */
    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove(changeX == 1 && changeY == 1, this, newX, newY, position);
        return Utils.attackOrMove(this, newX, newY, position);
    }

    public void reset() {
        if (hasMoved) hasMoved = false;
    }
}
