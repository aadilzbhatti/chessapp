package com.chessapp.api.game;

import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.utils.ChessPieceName;

/**
 * Will later be used to check the game conditions
 * (checkmate, stalemate, etc.)
 */
public class ChessConditions {

    /**
     * Used to check if the player is in a checkmate position. Checks each piece
     * to see if they can check the King in the next move. If any one of them can,
     * then the opposing player is in checkmate.
     *
     * @param player   The player to be examined
     * @param position The game with its current state, positions, etc.
     * @return Whether or not the opposing player is in checkmate
     */
    public static boolean inCheck(ChessPlayer player, BoardPosition position) {
        for (ChessPiece piece : player.pieces) {
            if (isKingInLineOfPiece(player, piece, position)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inStalemate(ChessPlayer player, ChessPlayer opposing, BoardPosition position) {
        return !inCheck(player, position) && !anyLegalMoves(player, opposing, position);
    }

    /**
     * The helper function which checks each individual piece to see if they
     * can check the King in the next move.
     *
     * @param player   The player to be examined
     * @param piece    The piece to be examined
     * @param position The board position manager
     * @return Whether or not the piece can check the King in the next move
     */
    private static boolean isKingInLineOfPiece(ChessPlayer player, ChessPiece piece, BoardPosition position) {
        if (piece.getName() == ChessPieceName.BISHOP) {
            return Utils.canBishopCheckKing(piece, position, player);

        } else if (piece.getName() == ChessPieceName.KNIGHT) {
            return Utils.canKnightCheckKing(piece, position, player);

        } else if (piece.getName() == ChessPieceName.ROOK) {
            return Utils.canRookCheckKing(piece, position, player);

        } else if (piece.getName() == ChessPieceName.KING) {
            return Utils.canKingCheckKing(piece, position, player);

        } else if (piece.getName() == ChessPieceName.PAWN) {
            return Utils.canPawnCheckKing(piece, position, player);

        } else {
            return Utils.canQueenCheckKing(piece, position, player);
        }
    }

    /**
     * Moves our king and checks if we can make a valid move, then resets
     *
     * @param myPlayer The player we are examining
     * @param opposing The player who can murder us
     * @param position The position manager
     * @return Whether or not we have any legal moves to make
     */
    private static boolean anyLegalMoves(ChessPlayer myPlayer, ChessPlayer opposing, BoardPosition position) {
        ChessPiece myKing = myPlayer.king;
        int currX = myKing.x();
        int currY = myKing.y();

        // left
        if (isValidMove(opposing, position, myKing, currX - 1, currY, currX, currY)) return true;

        // right
        if (isValidMove(opposing, position, myKing, currX + 1, currY, currX, currY)) return true;

        // up
        if (isValidMove(opposing, position, myKing, currX, currY - 1, currX, currY)) return true;

        // down
        if (isValidMove(opposing, position, myKing, currX, currY + 1, currX, currY)) return true;

        // nw
        if (isValidMove(opposing, position, myKing, currX - 1, currY - 1, currX, currY)) return true;

        // ne
        if (isValidMove(opposing, position, myKing, currX + 1, currY - 1, currX, currY)) return true;

        // sw
        if (isValidMove(opposing, position, myKing, currX - 1, currY + 1, currX, currY)) return true;

        // se
        return (!isValidMove(opposing, position, myKing, currX + 1, currY + 1, currX, currY));
    }

    private static boolean isValidMove(ChessPlayer player,
                                       BoardPosition position,
                                       ChessPiece myKing,
                                       int currX, int currY, int nextX, int nextY) {
        try {
            myKing.move(nextX, nextY, position);
            if (!inCheck(player, position)) return true;
            myKing.move(currX, currY, position);
        } catch (Exception e) {
            // we don't care what people say
        }
        return false;
    }


}
