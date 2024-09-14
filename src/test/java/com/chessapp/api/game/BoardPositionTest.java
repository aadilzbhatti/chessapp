package com.chessapp.api.game;

import com.chessapp.api.board.BoardPosition;
import com.chessapp.api.pieces.piece.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests board position manager
 */
public class BoardPositionTest {
    BoardPosition position;

    @BeforeEach
    public void setUp() {
        position = new BoardPosition();
    }

    /**
     * Tests that we can create the board position manager
     */
    @Test
    public void shouldBeAbleToCreateBoardPosition() {
        assertNotNull(position);
    }

    /**
     * Tests that we can initialize the board position
     * manager with two players and pieces
     */
    @Test
    public void shouldBeAbleToInitializeWithPieces() {
        ChessPlayer p1 = new ChessPlayer(PieceColor.WHITE, position);
        ChessPlayer p2 = new ChessPlayer(PieceColor.RED, position);
        for (int i = 0; i < position.getSize(); i++) {
            assertNotNull(position.getPieceAtPosition(i, 0)); // Regular pieces
            assertNotNull(position.getPieceAtPosition(i, 1)); // Pawns
            assertNotNull(position.getPieceAtPosition(i, 7)); // Regular pieces
            assertNotNull(position.getPieceAtPosition(i, 6)); // Pawns
        }
    }

    /**
     * Tests that the board position manager updates
     * upon pieces moving
     */
    @Test
    public void shouldChangeWhenPieceMoves() throws Exception {
        ChessPlayer p1 = new ChessPlayer(PieceColor.RED, position);
        Pawn p = p1.pawns[0];
        assertSame(p, position.getPieceAtPosition(0, 1));
        p.move(0, 3, position);
        assertSame(p, position.getPieceAtPosition(0, 3));
    }

    /**
     * Tests that we can make board position managers of
     * size N x N other than N = 8.
     */
    @Test
    public void shouldBeAbleToCreateBoardPositionOfNSize() {
        BoardPosition newBoard = new BoardPosition();
        assertEquals(newBoard.getSize(), 12);
    }

    /**
     * Should not be able to make smaller boards than N = 8
     */
    @Test
    public void shouldNotBeAbleToCreateSmaller() {
        assertThrows(AssertionError.class, () -> {
            BoardPosition newBoard = new BoardPosition();
            assertNull(newBoard);
        });
    }
}
