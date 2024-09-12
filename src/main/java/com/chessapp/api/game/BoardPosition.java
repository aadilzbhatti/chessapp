package com.chessapp.api.game;

import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.utils.InvalidPositionException;

/**
 * The board position manager, used to manage
 * the positions of all of the Chess pieces
 */
public class BoardPosition {

    // The internal array used to keep track of the location of pieces
    private final ChessPiece[][] positions;

    // The size of the board
    private final int boardSize;

    /**
     * Constructor for creating a board position manager
     * with a particular size
     *
     * @param _boardSize The desired size of the board (N x N)
     */
    public BoardPosition(int _boardSize) {
        assert (_boardSize >= 8);
        positions = new ChessPiece[_boardSize][_boardSize];
        boardSize = _boardSize;
    }

    /**
     * Default constructor for board manager, will default
     * to a size of N = 8
     */
    public BoardPosition() {
        positions = new ChessPiece[8][8];
        boardSize = 8;
    }

    /**
     * Determines if a particular position is occupied by a piece
     *
     * @param xPos The x-position of the piece
     * @param yPos The y-position of the piece
     * @return Whether or not the position is occupied
     */
    public Boolean isOccupied(int xPos, int yPos) {
        return positions[xPos][yPos] != null;
    }

    /**
     * Occupies a position in the position manager
     *
     * @param xPos  The desired x-position in the manager
     * @param yPos  The desired y-position in the manager
     * @param piece The desired piece to occupy the position
     * @throws InvalidPositionException thrown if the position is currently occupied
     */
    public void occupyPosition(int xPos, int yPos, ChessPiece piece) throws InvalidPositionException {
        if (isOccupied(xPos, yPos)) {
            throw new InvalidPositionException("Position is currently occupied by a piece");
        } else {
            positions[xPos][yPos] = piece;
        }
    }

    /**
     * Allows a piece to leave the position
     *
     * @param piece The desired piece to leave
     */
    public void leavePosition(ChessPiece piece) {
        positions[piece.x()][piece.y()] = null;
    }

    /**
     * Public getter for the size of the board
     *
     * @return The size of the board
     */
    public int getSize() {
        return boardSize;
    }

    /**
     * Public getter for getting piece at particular position
     *
     * @param xPos X-position we are reading from
     * @param yPos Y-position we are reading from
     * @return The piece at position (x,y) in the board
     */
    public ChessPiece getPieceAtPosition(int xPos, int yPos) {
        return positions[xPos][yPos];
    }
}
