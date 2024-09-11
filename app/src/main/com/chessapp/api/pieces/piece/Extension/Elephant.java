package com.chessapp.api.pieces.piece.Extension;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.piece.Utils;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;
import com.chessapp.api.pieces.utils.ChessSide;

/**
 * Elephant extension
 */
public class Elephant extends ChessPiece {
    /**
     * The constructor for creating positioned & colored Elephant pieces
     * @param color The desired color of the Elephant
     * @param side The desired side of the Elephant (left, right)
     * @param position The board position manager
     * @throws Exception thrown if invalid piece given
     */
    public Elephant(ChessPieceColor color, ChessSide side, BoardPosition position) throws Exception {
        super(ChessPieceName.ELEPHANT, color, side, position);

        int size = position.getSize();
        if (color == ChessPieceColor.WHITE) {
            Utils.setPosWithSide(this, 3, size - 4, size - 3, size - 4);
        } else {
            Utils.setPosWithSide(this, 3, 0, size - 3, 0);
        }
        position.occupyPosition(xPos, yPos, this);
    }

    /**
     * Allows Elephants to be barriers to the other pieces
     * @param newX - the new x-position of the piece, checked for validity
     * @param newY - the new y-position of the piece, also checked
     * @param position The board position manager
     * @throws Exception thrown if invalid position given
     */
    public void move(int newX, int newY, BoardPosition position) throws Exception {
        Utils.checkInitialBounds(newX, newY);

        int changeX = Math.abs(newX - this.xPos);
        int changeY = Math.abs(newY - this.yPos);

        Utils.checkValidMove(changeX == 0 && changeY == 0, this, newX, newY, position);
        Utils.makeValidMove(this, newX, newY, position);
    }

    /**
     * Allows Elephants to not be able to attack other pieces
     * @param newX The new x-position of the piece
     * @param newY The new y-position of the piece
     * @param position the board position manager
     * @throws Exception if invalid position given
     * @return The newly taken chess piece
     */
    public ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception {
        return null;
    }
}
