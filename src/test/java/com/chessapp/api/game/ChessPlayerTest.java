package com.chessapp.api.game;

import com.chessapp.api.board.BoardPosition;
import com.chessapp.api.pieces.piece.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the various Chess Player abilities
 */
public class ChessPlayerTest {
    BoardPosition position = new BoardPosition();

    /**
     * Tests that we can make Chess players
     */
    @Test
    public void shouldBeAbleToInitialize() {
        ChessPlayer p = new ChessPlayer(PieceColor.RED, position);
        assertNotNull(p);
    }

    /**
     * Tests that we can initialize chess players
     * and have all their pieces initialized
     */
    @Test
    public void shouldHaveAllPiecesAndList() {
        ChessPlayer p = new ChessPlayer(PieceColor.RED, position);
        assertNotNull(p.king);
        assertNotNull(p.queen);
        assertNotNull(p.leftBishop);
        assertNotNull(p.rightBishop);
        assertNotNull(p.leftKnight);
        assertNotNull(p.rightKnight);
        assertNotNull(p.leftRook);
        assertNotNull(p.rightRook);
        assertNotNull(p.pawns);
        for (Pawn pawn : p.pawns) {
            assertNotNull(pawn);
        }
    }

    /**
     * Tests that we can attack other players
     *
     * @throws Exception
     */
    @Test
    public void beAbleToCaptureOtherPlayers() throws Exception {
        ChessPlayer p1 = new ChessPlayer(PieceColor.RED, position);
        ChessPlayer p2 = new ChessPlayer(PieceColor.WHITE, position);
        p1.leftKnight.move(2, 2, position);
        p2.rightKnight.move(5, 5, position);
        p1.leftKnight.move(4, 3, position);
        p2.capture(p1.leftKnight, 4, 3, position, p1);
        assertTrue(p2.takenPieces.contains(p1.leftKnight));
        assertFalse(p1.pieces.contains(p1.leftKnight));
    }
}
