package com.chessapp.api.game;

import com.chessapp.api.board.BoardPosition;
import com.chessapp.api.pieces.piece.ChessPiece;

import static com.chessapp.ConstantsKt.BOARD_SIZE;

/**
 * Utility functions used for determining whether or not
 * the King is in checkmate.
 */
class Utils {

    /**
     * Used to check if a bishop is able to check the King. We check all diagonals -- if we
     * find the opposing player's King before any other piece, we know the King is in checkmate
     *
     * @param bishop   The piece, must be a Bishop in order for move-checking to work
     * @param position The board position manager
     * @param player   The player who owns the bishop
     * @return Whether or not the bishop can check the King
     */
    public static boolean canBishopCheckKing(ChessPiece bishop, BoardPosition position, ChessPlayer player) {
        int currX = bishop.x();
        int currY = bishop.y();

        int i = currX;
        int j = currY;
        while (i >= 0 && j >= 0) {
            ChessPiece chosen = position.getPieceAtPosition(i--, j--);
            if (chosen != null && chosen != bishop) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        i = currX;
        j = currY;
        while (i >= 0 && j < BOARD_SIZE) {
            ChessPiece chosen = position.getPieceAtPosition(i--, j++);
            if (chosen != null && chosen != bishop) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        i = currX;
        j = currY;
        while (i < position.getSize() && j >= 0) {
            ChessPiece chosen = position.getPieceAtPosition(i++, j--);
            if (chosen != null && chosen != bishop) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        i = currX;
        j = currY;
        while (i < position.getSize() && j < position.getSize()) {
            ChessPiece chosen = position.getPieceAtPosition(i++, j++);
            if (chosen != null && chosen != bishop) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Used to check if Knight pieces can checkmate King. Since there are only a fixed amount
     * of moves that Knights can make, we simply check each of the 8 cases.
     *
     * @param knight   The piece we are examining to see if it can checkmate the King
     * @param position The board position manager
     * @param player   The player we are examining
     * @return Whether or not the King can be checked by the Knight right now
     */
    public static boolean canKnightCheckKing(ChessPiece knight, BoardPosition position, ChessPlayer player) {
        int currX = knight.x();
        int currY = knight.y();

        if (currX + 2 < position.getSize() && currY + 1 < position.getSize()) {     // X + 2 & Y + 1
            ChessPiece chosen = position.getPieceAtPosition(currX + 2, currY + 1);
            if (isOpposingKing(chosen, player)) {
                return true;
            }
        }

        if (currX + 2 < position.getSize() && currY - 1 >= 0) {                      // X + 2 & Y - 1
            ChessPiece chosen = position.getPieceAtPosition(currX + 2, currY - 1);
            if (isOpposingKing(chosen, player)) {
                return true;
            }
        }

        if (currX - 2 >= 0 && currY - 1 >= 0) {                                       // X - 2 & Y - 1
            ChessPiece chosen = position.getPieceAtPosition(currX - 2, currY - 1);
            if (isOpposingKing(chosen, player)) {
                return true;
            }
        }

        if (currX - 2 >= 0 && currY + 1 < position.getSize()) {                      // X - 2 & Y + 1
            ChessPiece chosen = position.getPieceAtPosition(currX - 2, currY + 1);
            if (isOpposingKing(chosen, player)) {
                return true;
            }
        }

        if (currX + 1 < position.getSize() && currY + 2 < position.getSize()) {     // X + 1 & Y + 2
            ChessPiece chosen = position.getPieceAtPosition(currX + 1, currY + 2);
            if (isOpposingKing(chosen, player)) {
                return true;
            }
        }

        if (currX + 1 < position.getSize() && currY - 2 >= 0) {                      // X + 1 & Y - 2
            ChessPiece chosen = position.getPieceAtPosition(currX + 1, currY - 2);
            if (isOpposingKing(chosen, player)) {
                return true;
            }
        }

        if (currX - 1 >= 0 && currY + 2 < position.getSize()) {                      // X - 1 & Y + 2
            ChessPiece chosen = position.getPieceAtPosition(currX - 1, currY + 2);
            if (isOpposingKing(chosen, player)) {
                return true;
            }
        }

        if (currX - 1 >= 0 && currY - 2 >= 0) {                                       // X - 1 & Y - 2
            ChessPiece chosen = position.getPieceAtPosition(currX - 1, currY - 2);
            return isOpposingKing(chosen, player);
        }
        return false;
    }

    /**
     * Used to determine if Rook can checkmate King in one move. Here, we check to see
     * if there are any possibilities along the columns / rows of the board corresponding
     * to the Rook.
     *
     * @param rook     The piece we are examining
     * @param position The board position manager
     * @param player   The player who owns the Rook
     * @return Whether or not the King can be checked by the Rook in the next move
     */
    public static boolean canRookCheckKing(ChessPiece rook, BoardPosition position, ChessPlayer player) {
        int currX = rook.x();
        int currY = rook.y();

        for (int i = currX; i >= 0; i--) {
            ChessPiece chosen = position.getPieceAtPosition(i, currY);
            if (chosen != null && chosen != rook) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = currX; i < position.getSize(); i++) {
            ChessPiece chosen = position.getPieceAtPosition(i, currY);
            if (chosen != null && chosen != rook) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = currY; i >= 0; i--) {
            ChessPiece chosen = position.getPieceAtPosition(currX, i);
            if (chosen != null && chosen != rook) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = currY; i < position.getSize(); i++) {
            ChessPiece chosen = position.getPieceAtPosition(currX, i);
            if (chosen != null && chosen != rook) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        return false;
    }

    /**
     * Used to see if the pawn can checkmate the King in the next turn. We simply check the upper-right
     * and upper-left cells to see if the King is there.
     *
     * @param pawn     The piece we are examining
     * @param position The board position manager
     * @param player   The current player
     * @return Whether or not the pawn can check the King in the next move
     */
    public static boolean canPawnCheckKing(ChessPiece pawn, BoardPosition position, ChessPlayer player) {
        int currX = pawn.x();
        int currY = pawn.y();

        if (currX - 1 >= 0 && currY - 1 >= 0) {
            ChessPiece chosen = position.getPieceAtPosition(currX - 1, currY - 1);
            if (chosen != null) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                }
            }
        }
        if (currX + 1 < position.getSize() && currY - 1 >= 0) {
            ChessPiece chosen = position.getPieceAtPosition(currX + 1, currY - 1);
            if (chosen != null) {
                return isOpposingKing(chosen, player);
            }
        }
        return false;
    }

    /**
     * Used to see whether or not the Queen can check the King in the next move. Since the Queen is
     * essentially a Rook + Bishop combined, we can compose the checks for Bishops and Rooks as that
     * of the Queen.
     *
     * @param queen    The piece to be examined
     * @param position The board position manager
     * @param player   The player who owns the Queen
     * @return Whether or not the Queen can check the King in the next move.
     */
    public static boolean canQueenCheckKing(ChessPiece queen, BoardPosition position, ChessPlayer player) {
        return canBishopCheckKing(queen, position, player)
                || canRookCheckKing(queen, position, player);
    }

    /**
     * Used to see whether or not the King can check the King in the next move (oh boy). Since the King is
     * able to move in all directions but with only 1 space, we can simply check all the cases and add in
     * the check for the Pawn since they share two moves.
     *
     * @param king     The KING who is trying to check the other KING
     * @param position The board position manager
     * @param player   The player who owns the King
     * @return Whether or not the King can check the King in the next move (oof).
     */
    public static boolean canKingCheckKing(ChessPiece king, BoardPosition position, ChessPlayer player) {
        int currX = king.x();
        int currY = king.y();

        if (currY - 1 >= 0) {                                                   // directly above
            ChessPiece chosen = position.getPieceAtPosition(currX, currY - 1);
            if (chosen != null) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                }
            }
        }

        if (currY + 1 < position.getSize()) {                                   // directly below
            ChessPiece chosen = position.getPieceAtPosition(currX, currY + 1);
            if (chosen != null) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                }
            }
        }

        if (currX - 1 <= 0) {                                                   // to the left (to the left)
            ChessPiece chosen = position.getPieceAtPosition(currX - 1, currY);
            if (chosen != null) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                }
            }
        }

        if (currX + 1 < position.getSize()) {                                   // to the right
            ChessPiece chosen = position.getPieceAtPosition(currX + 1, currY);
            if (chosen != null) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                }
            }
        }

        if (currX - 1 >= 0 && currY + 1 < position.getSize()) {                 // bottom left
            ChessPiece chosen = position.getPieceAtPosition(currX - 1, currY + 1);
            if (chosen != null) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                }
            }
        }

        if (currX + 1 < position.getSize() && currY + 1 < position.getSize()) { // bottom right;
            ChessPiece chosen = position.getPieceAtPosition(currX + 1, currY + 1);
            if (chosen != null) {
                if (isOpposingKing(chosen, player)) {
                    return true;
                }
            }
        }

        return canPawnCheckKing(king, position, player);
    }

    /**
     * Helper function to determine if the piece encountered is the other player's King.
     *
     * @param piece  The piece which may or may not be the opposing King
     * @param player The current player
     * @return Whether or not the piece is the other player's king
     */
    private static boolean isOpposingKing(ChessPiece piece, ChessPlayer player) {
        return piece != null && piece.getName() == PieceName.KING && piece != player.king;
    }
}
