package com.chessapp.api.pieces.piece;

import com.chessapp.api.board.BoardPosition;
import com.chessapp.api.pieces.utils.ChessSide;
import com.chessapp.api.pieces.utils.InvalidPositionException;

/**
 * Utility functions for chess pieces, should only be
 * accessible to ChessPieces
 */
public class Utils {
    /**
     * A method used to set the position of a particular chess piece given the side (left, right) of the piece.
     *
     * @param piece  The piece whose position needs to be set
     * @param leftX  The x-position if the piece is on the left
     * @param leftY  The y-position if the piece is on the left
     * @param rightX The x-position if the piece is on the right
     * @param rightY The y-position if the piece is on the right
     */
    public static void setPosWithSide(ChessPiece piece, int leftX, int leftY, int rightX, int rightY) {
        if (piece.getSide() == ChessSide.LEFT) {
            piece.setPos(leftX, leftY);
        } else {
            piece.setPos(rightX, rightY);
        }
    }

    /**
     * Checks the initial bounds of a movement to see if they are off the board
     *
     * @param newX The new x-position of the piece
     * @param newY The new y-position of the piece
     * @throws InvalidPositionException Thrown if the final position is off the board
     */
    public static void checkInitialBounds(int newX, int newY) throws InvalidPositionException {
        if (newX > 7 || newY > 7 || newX < 0 || newY < 0) {
            throw new InvalidPositionException("Final position is off board");
        }
    }

    /**
     * Checks if the move is valid based on a condition passed in
     *
     * @param invalidMoveCondition The condition (i.e. rook only moves up-down or left-right, etc.)
     * @throws InvalidPositionException Thrown if move is invalid
     */
    public static void checkValidMove(boolean invalidMoveCondition,
                                      ChessPiece mine, int newX, int newY,
                                      BoardPosition position) throws InvalidPositionException {
        if (!invalidMoveCondition) {
            throw new InvalidPositionException("Final position is result of an invalid move.");
        }

        if (mine.getName() == PieceName.BISHOP) {
            if (Utils.bishopPiecesInWay(mine, newX, newY, position)) {
                throw new InvalidPositionException("Cannot move when pieces in way");
            }
        } else if (mine.getName() == PieceName.ROOK) {
            if (Utils.rookPiecesInWay(mine, newX, newY, position)) {
                throw new InvalidPositionException("Cannot move when pieces in way");
            }
        } else if (mine.getName() == PieceName.QUEEN) {
            if (Utils.queenPiecesInWay(mine, newX, newY, position)) {
                throw new InvalidPositionException("Cannot move when pieces in way");
            }
        }
    }

    /**
     * Makes the valid move checked previously
     *
     * @param piece    the piece making the move
     * @param newX     the new x-position of the piece
     * @param newY     the new y-position of the piece
     * @param position the board position object holding the other pieces
     * @throws InvalidPositionException
     */
    public static void makeValidMove(ChessPiece piece, int newX, int newY, BoardPosition position) throws InvalidPositionException {
        position.removePiece(piece);
        piece.setPos(newX, newY);
        position.occupyPosition(newX, newY, piece);
    }

    /**
     * Takes piece at position and makes move to that position
     *
     * @param mine     The current attacking piece
     * @param newX     The new x-position (where the old piece is)
     * @param newY     The new y-position
     * @param position The board position manager
     * @throws InvalidPositionException
     */
    public static ChessPiece takePieceAndMove(ChessPiece mine, int newX, int newY, BoardPosition position) throws InvalidPositionException {
        ChessPiece taken = position.getPieceAtPosition(newX, newY);
        position.removePiece(taken);
        makeValidMove(mine, newX, newY, position);
        return taken;
    }

    /**
     * Either moves piece or attacks depending on whether there is a piece in
     * the final specified position
     *
     * @param mine     The piece attacking or moving
     * @param newX     The destined x-position
     * @param newY     The destined y-position
     * @param position The board position manager
     * @throws InvalidPositionException
     */
    public static ChessPiece attackOrMove(ChessPiece mine, int newX, int newY, BoardPosition position) throws InvalidPositionException {
        if (!position.isOccupied(newX, newY)) {
            Utils.makeValidMove(mine, newX, newY, position);
            return null;
        } else {
            return Utils.takePieceAndMove(mine, newX, newY, position);
        }
    }

    /**
     * Checks if there are any pieces in the Bishop's way (of same color)
     *
     * @param mine     My bishop
     * @param xPos     The desired x-position
     * @param yPos     The desired y-position
     * @param position The board position manager
     * @return Whether or not any pieces are in the Bishop's way
     */
    private static boolean bishopPiecesInWay(ChessPiece mine, int xPos, int yPos, BoardPosition position) {
        int currX = mine.x();
        int currY = mine.y();

        int i = currX;
        int j = currY;
        while (i >= xPos && j >= yPos) {
            ChessPiece chosen = position.getPieceAtPosition(i--, j--);
            if (isMyPieceInWay(chosen, mine)) {
                return true;
            }
        }

        i = currX;
        j = currY;
        while (i >= xPos && j <= yPos) {
            ChessPiece chosen = position.getPieceAtPosition(i--, j++);
            if (isMyPieceInWay(chosen, mine)) {
                return true;
            }
        }

        i = currX;
        j = currY;
        while (i <= xPos && j >= yPos) {
            ChessPiece chosen = position.getPieceAtPosition(i++, j--);
            if (isMyPieceInWay(chosen, mine)) {
                return true;
            }
        }

        i = currX;
        j = currY;
        while (i <= xPos && j <= yPos) {
            ChessPiece chosen = position.getPieceAtPosition(i++, j++);
            if (isMyPieceInWay(chosen, mine)) {
                return true;
            }
        }
        return false;
    }

    private static boolean rookPiecesInWay(ChessPiece mine, int xPos, int yPos, BoardPosition position) {
        int currX = mine.x();
        int currY = mine.y();

        if (currX > xPos && currY == yPos) {
            for (int i = currX; i >= xPos; i--) {
                ChessPiece chosen = position.getPieceAtPosition(i, currY);
                if (isMyPieceInWay(chosen, mine)) {
                    return true;
                }
            }
        }

        if (currX < xPos && currY == yPos) {
            for (int i = currX; i <= xPos; i++) {
                ChessPiece chosen = position.getPieceAtPosition(i, currY);
                if (isMyPieceInWay(chosen, mine)) {
                    return true;
                }
            }
        }

        if (currX == xPos && currY > yPos) {
            for (int i = currY; i >= yPos; i--) {
                ChessPiece chosen = position.getPieceAtPosition(currX, i);
                if (isMyPieceInWay(chosen, mine)) {
                    System.out.println(chosen.getName().toString());
                    return true;
                }
            }
        }

        if (currX == xPos && currY < yPos) {
            for (int i = currY; i <= yPos; i++) {
                ChessPiece chosen = position.getPieceAtPosition(currX, i);
                if (isMyPieceInWay(chosen, mine)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean queenPiecesInWay(ChessPiece mine, int xPos, int yPos, BoardPosition position) {
        return bishopPiecesInWay(mine, xPos, yPos, position)
                || rookPiecesInWay(mine, xPos, yPos, position);
    }

    private static boolean isMyPieceInWay(ChessPiece chosen, ChessPiece mine) {
        return chosen != null && chosen != mine && chosen.getColor() == mine.getColor();
    }
}
