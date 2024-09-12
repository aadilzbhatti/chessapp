package com.chessapp.api.pieces;

import com.chessapp.api.game.BoardPosition;
import com.chessapp.api.game.ChessPlayer;
import com.chessapp.api.pieces.piece.Bishop;
import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.piece.King;
import com.chessapp.api.pieces.piece.Knight;
import com.chessapp.api.pieces.piece.Pawn;
import com.chessapp.api.pieces.piece.Queen;
import com.chessapp.api.pieces.piece.Rook;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.api.pieces.utils.ChessSide;
import com.chessapp.api.pieces.utils.InvalidPositionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testing various properties and abilities
 * of the different chess pieces
 */

public class ChessPieceTest {
    BoardPosition position;

    @BeforeEach
    public void setUp() {
        position = new BoardPosition();
    }

    /**
     * Test the creation of a single chess piece
     */
    @Test
    public void beAbleToCreateBishop() {
        try {
            Bishop b = new Bishop(ChessPieceColor.RED, ChessSide.LEFT, position);
            assertEquals(b.x(), 2);
            assertEquals(b.y(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the creation of Queens
     */
    @Test
    public void beAbleToCreateQueen() {
        try {
            Queen q = new Queen(ChessPieceColor.RED, position);
            assertEquals(q.x(), 3);
            assertEquals(q.y(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the creation of Kings
     */
    @Test
    public void beAbleToCreateKing() {
        try {
            King k = new King(ChessPieceColor.RED, position);
            assertEquals(k.x(), 4);
            assertEquals(k.y(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the creation of Rooks
     */
    @Test
    public void beAbleToCreateRook() {
        try {
            Rook r = new Rook(ChessPieceColor.RED, ChessSide.LEFT, position);
            assertEquals(r.x(), 0);
            assertEquals(r.y(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void beAbleToCreatePawn() {
        try {
            Pawn p = new Pawn(ChessPieceColor.RED, position);
            p.setInitialPawnPosition(4, position);
            assertEquals(p.x(), 4);
            assertEquals(p.y(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the ability to move properly
     */
    @Test
    public void beAbleToMove() throws Exception {
        Bishop b = new Bishop(ChessPieceColor.RED, ChessSide.LEFT, position);
        b.move(0, 2, position);
    }

    /**
     * Tests moving to an occupied position
     *
     * @throws Exception since the position is occupied
     */
    @Test
    public void occupiedPositionTest() throws Exception {
        Bishop b = new Bishop(ChessPieceColor.RED, ChessSide.LEFT, position);
        b.move(1, 3, position);
        Pawn p = new Pawn(ChessPieceColor.RED, position);
        p.setInitialPawnPosition(3, position);
        assertThrows(InvalidPositionException.class, () -> position.occupyPosition(p.x(), p.y(), p));
    }

    /**
     * Tests moving to an invalid position (invalid move for the piece)
     *
     * @throws Exception since the piece is making an invalid move
     */
    @Test
    public void badMoveTest() throws Exception {
        Bishop b = new Bishop(ChessPieceColor.RED, ChessSide.LEFT, position);
        assertThrows(InvalidPositionException.class, () -> b.move(0, 0, position));
    }

    /**
     * Tests the common creation of an amount of pawns
     */
    @Test
    public void beAbleToMakeManyPawns() {
        try {
            Pawn[] pawns = new Pawn[8];
            for (int i = 0; i < pawns.length; i++) {
                pawns[i] = new Pawn(ChessPieceColor.RED, position);
                pawns[i].setInitialPawnPosition(i, position);
                assertNotNull(pawns[i]);
            }
            assertNotNull(pawns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests trying to move to an off-board position
     *
     * @throws Exception thrown since we are trying to move off board
     */
    @Test
    public void badInitialPosition() throws Exception {
        Bishop b = new Bishop(ChessPieceColor.RED, ChessSide.LEFT, position);
        assertThrows(InvalidPositionException.class, () -> b.move(48, 49, position));
    }

    /**
     * Tests the ability to attack other pieces
     *
     * @throws Exception
     */
    @Test
    public void attackOtherPieces() throws Exception {
        Knight k1 = new Knight(ChessPieceColor.RED, ChessSide.RIGHT, position);
        Knight k2 = new Knight(ChessPieceColor.WHITE, ChessSide.LEFT, position);
        k1.move(4, 5, position);
        k2.move(2, 2, position);
        k2.move(4, 3, position);
        ChessPiece returned = k1.attack(4, 3, position);
        assertSame(k1, position.getPieceAtPosition(4, 3));
        assertSame(k2, returned);
    }

    /**
     * Tests the fact that we simply move if no piece is
     * at the attacking position
     *
     * @throws Exception
     */
    @Test
    public void attackWithNoPiece() throws Exception {
        Knight k1 = new Knight(ChessPieceColor.RED, ChessSide.RIGHT, position);
        ChessPiece returned = k1.attack(4, 5, position);
        assertSame(k1, position.getPieceAtPosition(4, 5));
        assertNull(returned);
    }

    /**
     * Tests that the rook can attack properly
     *
     * @throws Exception
     */
    @Test
    public void testRookAttackingOthers() throws Exception {
        Rook r1 = new Rook(ChessPieceColor.WHITE, ChessSide.LEFT, position);
        Rook r2 = new Rook(ChessPieceColor.RED, ChessSide.LEFT, position);
        ChessPiece returned = r1.attack(r2.x(), r2.y(), position);
        assertSame(returned, r2);
        assertSame(r1, position.getPieceAtPosition(0, 0));
    }

    /**
     * Tests that bishop can attack properly
     *
     * @throws Exception
     */
    @Test
    public void testBishopCanAttackOthers() throws Exception {
        Bishop b1 = new Bishop(ChessPieceColor.RED, ChessSide.LEFT, position);
        Pawn p1 = new Pawn(ChessPieceColor.WHITE, position);
        p1.setInitialPawnPosition(4, position);
        b1.move(6, 4, position);
        ChessPiece returned = b1.attack(p1.x(), p1.y(), position);
        assertSame(returned, p1);
        assertSame(b1, position.getPieceAtPosition(4, 6));
    }

    /**
     * Tests that King can attack properly
     *
     * @throws Exception
     */
    @Test
    public void testKingCanAttackOthers() throws Exception {
        King k1 = new King(ChessPieceColor.WHITE, position);
        Bishop other = new Bishop(ChessPieceColor.RED, ChessSide.RIGHT, position);
        other.move(1, 4, position);
        other.move(3, 6, position);
        ChessPiece returned = k1.attack(other.x(), other.y(), position);
        assertSame(returned, other);
        assertSame(k1, position.getPieceAtPosition(3, 6));
    }

    @Test
    public void testCannotMoveBishopWhenPiecesInWay() throws Exception {
        ChessPlayer p1 = new ChessPlayer(ChessPieceColor.RED, position);
        Bishop b = p1.leftBishop;
        assertThrows(InvalidPositionException.class, () -> {
            b.move(4, 1, position);
            b.move(2, 1, position);
        });
    }

    @Test
    public void testCannotMoveRookWhenPiecesInWay() throws Exception {
        ChessPlayer p1 = new ChessPlayer(ChessPieceColor.RED, position);
        Rook r = p1.leftRook;
        assertThrows(InvalidPositionException.class, () -> {
            r.move(0, 4, position);
            r.move(4, 0, position);
        });
    }
}
