package com.chessapp.api.pieces.piece.Extension;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.piece.Utils;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;
import com.chessapp.api.pieces.utils.ChessSide;

/**
 * Default template for Giraffes
 */
public class Giraffe extends ChessPiece {
    /**
     * The constructor for creating positioned & colored Giraffe pieces
     *
     * @param color    The desired color of the Giraffe
     * @param side     The desired side of the Giraffe (left, right)
     * @param position The board position manager
     * @throws Exception thrown if invalid piece given
     */
    public Giraffe(ChessPieceColor color, ChessSide side, BoardPosition position) throws Exception {
        super(ChessPieceName.GIRAFFE, color, side, position);

        int size = position.getSize();
        if (color == ChessPieceColor.WHITE) {
            Utils.setPosWithSide(this, 4, size - 5, size - 4, size - 5);
        } else {
            Utils.setPosWithSide(this, 4, size - 2, size - 4, size - 2);
        }
        position.occupyPosition(xPos, yPos, this);
    }

    /**
     * Allows Giraffes to be move in any position at all
     *
     * @param newX     - the new x-position of the piece, checked for validity
     * @param newY     - the new y-position of the piece, also checked
     * @param position The board position manager
     * @throws Exception thrown if invalid position given
     */
    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove(true, this, newX, newY, position);
        Utils.makeValidMove(this, newX, newY, position);
    }

    /**
     * Allows Giraffes to be able to attack other Elephants
     *
     * @param newX     The new x-position of the piece
     * @param newY     The new y-position of the piece
     * @param position the board position manager
     * @return The newly taken chess piece
     * @throws Exception if invalid position given
     */
    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        if (position.getPieceAtPosition(newX, newY).getName() == ChessPieceName.ELEPHANT) {
            return Utils.attackOrMove(this, newX, newY, position);
        }
        return null;
    }
}