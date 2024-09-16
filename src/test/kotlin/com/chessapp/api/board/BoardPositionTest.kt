package com.chessapp.api.board

import com.chessapp.api.pieces.utils.InvalidPositionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardPositionTest {
    @BeforeEach
    fun setup() {
       BoardManager
    }

    @Test
    fun testOccupyPosition() {
        BoardManager.initialize()
        // should fail since we already occupied all these positions
        assertThrows<InvalidPositionException> {
            BoardManager.allPieces().forEach { piece -> BoardPosition.occupyPosition(piece) }
        }
    }

    @Test
    fun testGetPieceAtPosition() {
        BoardManager.allPieces().forEach { piece ->
            val gotPiece = BoardPosition.getPieceAtPosition(piece.rank(), piece.file())
            assertNotNull(gotPiece)
            assertEquals(piece, gotPiece)
        }
    }

    @Test
    fun removePiece() {
        BoardManager.allPieces().forEach { piece ->
            assertEquals(piece, BoardPosition.removePiece(piece))
            assertFalse(BoardPosition.isPositionOccupiedByPiece(piece.rank(), piece.file()))
        }
    }
}