package com.chessapp.gui;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.game.ChessConditions;
import com.chessapp.api.game.ChessPlayer;
import com.chessapp.api.game.GameState;
import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.piece.Pawn;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;
import com.chessapp.api.pieces.utils.InvalidPositionException;
import com.chessapp.gui.utils.Point;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * Linker between API and GUI, used from front-end to
 * communicate with back-end
 */
public class APILinker {
    boolean checkmate = false;
    private ChessPieceColor turn;
    private final ChessPlayer redPlayer;
    private final ChessPlayer whitePlayer;
    private final BoardPosition position;

    /**
     * Default constructor, initializes the players and the holy board
     * position manager we have been talking about for three weeks.
     */
    public APILinker() {
        position = new BoardPosition();
        redPlayer = new ChessPlayer(ChessPieceColor.RED, position);
        whitePlayer = new ChessPlayer(ChessPieceColor.WHITE, position);
        turn = ChessPieceColor.WHITE;
    }

    /**
     * Change the current turn
     */
    public void changeTurn() {
        if (turn == ChessPieceColor.RED) {
            turn = ChessPieceColor.WHITE;
        } else {
            turn = ChessPieceColor.RED;
        }
    }

    /**
     * Get the current turn
     *
     * @return The current turn
     */
    public ChessPieceColor currentTurn() {
        return turn;
    }

    /**
     * Try to move the piece in our API to make sure we are not making any
     * illegal moves here, and to see if we are attacking any other pieces.
     *
     * @param image The raw image pointing to the Chess Piece on the actual board
     * @param xPos  The desired x-coordinate
     * @param yPos  The desired y-coordinate
     * @throws Exception If we are making invalid moves
     */
    public void tryMove(ImageView image, double xPos, double yPos) throws Exception {
        ChessPlayer currentPlayer = getPlayerForTurn(turn);
        ChessPlayer otherPlayer = getOtherPlayer(currentPlayer);

        Point currentPos = getPosFromCoords(image.getTranslateX(), image.getTranslateY());
        Point targetSquare = getPosFromCoords(xPos, yPos);

        ChessPiece currentPiece = position.getPieceAtPosition(currentPos.X(), currentPos.Y());
        ChessPiece attacked;
        if ((attacked = position.getPieceAtPosition(targetSquare.X(), targetSquare.Y())) != null) {
            if (attacked.getColor() == turn) {
                throw new InvalidPositionException("Cannot attack your own pieces");
            }
            currentPlayer.attack(currentPiece, attacked.x(), attacked.y(), position, otherPlayer);
            if (attacked.getName() == ChessPieceName.KING) {
                checkmate = true;
            }
            removeAttackedPiece(ChessBoard.boardSquares[targetSquare.Y()][targetSquare.X()]);
        } else {
            currentPiece.move(targetSquare.X(), targetSquare.Y(), position);
        }
    }

    /**
     * Checks the game conditions, will throw an alert in the GUI.
     *
     * @return The current state of the game
     */
    public GameState checkGameConditions() {
        ChessPlayer currentPlayer = getPlayerForTurn(turn);
        if (checkmate) {
            return GameState.CHECKMATE;
        } else if (ChessConditions.inCheck(currentPlayer, position)) {
            return GameState.CHECK;
        } else if (ChessConditions.inStalemate(currentPlayer, getOtherPlayer(currentPlayer), position)) {
            return GameState.STALEMATE;
        } else {
            return GameState.NOTHING;
        }
    }

    /**
     * Our undo function which is connected to the undo in the GUI. Used to do
     * some internal cleanup in the API like moving pieces around.
     *
     * @param oldX The old x-coordinate of the piece
     * @param oldY The old y-coordinate of the piece
     * @param newX The new x-coordinate of the piece
     * @param newY The new y-coordinate of the piece
     * @throws Exception If things go wrong..
     */
    public void undoMove(double oldX, double oldY, double newX, double newY) throws Exception {
        Point currentPos = getPosFromCoords(oldX, oldY);
        Point nextPos = getPosFromCoords(newX, newY);
        ChessPiece currentPiece = position.getPieceAtPosition(currentPos.X(), currentPos.Y());
        position.leavePosition(currentPiece);
        currentPiece.setPos(nextPos.X(), nextPos.Y());
        position.occupyPosition(nextPos.X(), nextPos.Y(), currentPiece);
        if (currentPiece.getName() == ChessPieceName.PAWN) {
            Pawn p = (Pawn) currentPiece;
            p.reset();
        }
    }

    /**
     * Gets us the current playing player
     *
     * @param color The color allowed to move this turn
     * @return The player allowed to make moves this turn
     */
    private ChessPlayer getPlayerForTurn(ChessPieceColor color) {
        return color == ChessPieceColor.RED ? redPlayer : whitePlayer;
    }

    /**
     * The opposing player given a player
     *
     * @param currentPlayer The player whose opponent we want
     * @return The opponent of the current player
     */
    private ChessPlayer getOtherPlayer(ChessPlayer currentPlayer) {
        return currentPlayer.pieceColor == ChessPieceColor.RED ? whitePlayer : redPlayer;
    }

    /**
     * Extremely helpful function used to grab our API position ({(x, y) : x,y \in {0..7}})
     * from the raw x and y coordinates used in the GUI to place nodes
     *
     * @param xPos The raw x-coordinate we want to convert
     * @param yPos The raw y-coordinate we want to convert
     * @return The API position of some coordinates in the GUI
     */
    private Point getPosFromCoords(double xPos, double yPos) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle currentSquare = ChessBoard.boardSquares[i][j];
                double sqrX = currentSquare.getTranslateX();
                double sqrY = currentSquare.getTranslateY();
                if (sqrX == xPos && sqrY == yPos) {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    /**
     * Removes an attacked piece from the target square
     *
     * @param targetSquare The square from which we are removing a piece.
     */
    private void removeAttackedPiece(Rectangle targetSquare) {
        for (ImageView i : ChessBoard.getViews()) {
            double currX = i.getTranslateX();
            double currY = i.getTranslateY();
            if (currX == targetSquare.getTranslateX()
                    && currY == targetSquare.getTranslateY()) {
                ChessBoard.removeChessImage(i);
            }
        }
    }
}

