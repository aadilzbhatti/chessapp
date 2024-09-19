package com.chessapp.api.board

import com.chessapp.api.pieces.utils.InvalidPositionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardPositionTest {

    @Test
    fun testOccupyPosition() {
        BoardManager.initialize()
        // should fail since we already occupied all these positions
        assertThrows<InvalidPositionException> {
            BoardManager.allPieces().forEach { piece -> BoardManager.boardPosition().occupyPosition(piece) }
        }
    }

    @Test
    fun testGetPieceAtPosition() {
        BoardManager.allPieces().forEach { piece ->
            val gotPiece = BoardManager.boardPosition().getPieceAtPosition(piece.file(), piece.rank())
            assertNotNull(gotPiece)
            assertEquals(piece, gotPiece)
        }
    }

    @Test
    fun removePiece() {
        BoardManager.allPieces().forEach { piece ->
            assertEquals(piece, BoardManager.boardPosition().removePiece(piece))
            assertFalse(BoardManager.boardPosition().isPositionOccupiedByPiece(piece.file(), piece.rank()))
        }
    }
}