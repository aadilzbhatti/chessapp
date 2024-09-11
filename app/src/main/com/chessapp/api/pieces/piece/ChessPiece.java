package com.chessapp.api.pieces.piece;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessPieceName;
import com.chessapp.api.pieces.utils.ChessSide;

/**
 * The ChessPiece class is used as a template for creating
 * more specific Chess pieces (Rook, Knight, Bishop, King, Queen, Pawn).
 * Each piece has its own idiosyncrasies and as such must be implemented
 * slightly differently
 */
public abstract class ChessPiece {

    /**
     * Instance variables.
     * "name" is the ChessPieceName (one of the 6 possible pieces).
     * "color" is the ChessPieceColor (one of {white, red})
     * "side" is a value from {left, right} which applies to Knights, Rooks, and Bishops. Kings/Queens have their side
     *      determined by their color, and pawns have no side.
     * "xPos" is the x-position of the piece, similar for "yPos"
     */
    protected ChessPieceName name;
    protected ChessPieceColor color;
    protected ChessSide side;
    protected BoardPosition position;

    protected int xPos;
    protected int yPos;

    /**
     * The more commonly used constructor. Used to build pieces from
     *  {Knight, Rook, Bishop} as it uses a name, a color, and a side.
     *  Cannot be used to create Pawns, Kings, or Queens.
     *
     * @param _name The name of the piece
     * @param _color The color of the piece
     * @param _side The side (left, right) of the piece.
     */
    public ChessPiece(ChessPieceName _name, ChessPieceColor _color, ChessSide _side, BoardPosition _position) {
        name = _name;
        color = _color;
        side = _side;
        position = _position;
    }

    /**
     * The constructor used to create Kings and Queens. Cannot be used
     * to create pieces of any other type.
     *
     * @param _name The name of the piece (King, Queen)
     * @param _color The color of the piece.
     * @param _position The board position used to maintain all pieces
     */
    public ChessPiece(ChessPieceName _name, ChessPieceColor _color, BoardPosition _position) throws Exception {
        name = _name;
        color = _color;
        position = _position;
    }

    /**
     * Meant for pawns. Effectively cannot be used to generate pieces other than pawns.
     * @param _color The color associating with the White player or the Red player
     * @param _position The board position to maintain all pieces
     */
    public ChessPiece(ChessPieceColor _color, BoardPosition _position) {
        color = _color;
        name = ChessPieceName.PAWN;
        position = _position;
    }

    /**
     * Public getter for x-position.
     * @return x-position of current piece
     */
    public int x() {
        return xPos;
    }

    /**
     * Public getter for y-position
     * @return y-position of current piece
     */
    public int y() {
        return yPos;
    }

    /**
     * Public getter for name of piece
     * @return name of piece
     */
    public ChessPieceName getName() {
        return name;
    }

    public ChessPieceColor getColor() {
        return color;
    }

    /**
     * Unsafe method to set the position of a piece. Only used by
     * subclasses of ChessPiece, and only called within "move"
     *
     * @param x The new x-position of the piece
     * @param y The new y-position of the piece
     */
    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * The move function. Since each piece moves differently, must be
     * implemented for each individual piece. Only call setPos from here
     * with robust error-checking.
     *
     * @param newX The new x-position of the piece, checked for validity
     * @param newY The new y-position of the piece, also checked
     * @throws Exception - if the new position is invalid
     */
    public abstract void move(int newX, int newY, BoardPosition position) throws Exception;

    /**
     * The attack method. Since every piece attacks differently, must be
     * implemented individually. Will *also* call move
     *
     * @param newX The new x-position of the piece
     * @param newY The new y-position of the piece
     * @param position the board position manager
     * @return the taken Chess piece
     */
    public abstract ChessPiece attack(int newX, int newY, BoardPosition position) throws Exception;
}


// TODO add thing if pieces in way..