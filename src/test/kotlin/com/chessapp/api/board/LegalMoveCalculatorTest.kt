package com.chessapp.api.board

import com.chessapp.api.pieces.piece.ChessPiece
import com.chessapp.api.pieces.piece.Knight
import com.chessapp.api.pieces.piece.PieceColor
import com.chessapp.api.pieces.piece.Rook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LegalMoveCalculatorTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideStartingPositionTestCases")
    fun testLegalMovesStartingPosition(name: String, piece: ChessPiece, expectedLegalMoves: Set<Pair<Rank, Int>>) {
        val gotLegalMoves = LegalMoveCalculator.getLegalMovesForPiece(piece)
        assertNotNull(gotLegalMoves)
        println(gotLegalMoves)
        gotLegalMoves?.let { assertMoveSetsAreEquivalent(expectedLegalMoves, gotLegalMoves)}
    }

    fun testLegalMovesRookInCenter() {

    }

    companion object {
        @JvmStatic
        fun provideStartingPositionTestCases(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("White queenside rook", Rook(PieceColor.WHITE, Rank.A, 1), emptySet<Pair<Rank, Int>>()),
                Arguments.of("White kingside rook", Rook(PieceColor.WHITE, Rank.H, 1), emptySet<Pair<Rank, Int>>()),
                Arguments.of("Black queenside rook", Rook(PieceColor.BLACK, Rank.A, 8), emptySet<Pair<Rank, Int>>()),
                Arguments.of("Black kingside rook", Rook(PieceColor.BLACK, Rank.H, 8), emptySet<Pair<Rank, Int>>()),
                Arguments.of("White queenside knight", Knight(PieceColor.WHITE, Rank.B, 1), setOf(Rank.A to 3, Rank.C to 3)),
                Arguments.of("White kingside knight", Knight(PieceColor.WHITE, Rank.G, 1), setOf(Rank.F to 3, Rank.H to 3)),
                Arguments.of("Black queenside knight", Knight(PieceColor.BLACK, Rank.B, 8), setOf(Rank.A to 6, Rank.C to 6)),
                Arguments.of("Black kingside knight", Knight(PieceColor.BLACK, Rank.G, 8), setOf(Rank.F to 6, Rank.H to 6)),
            )
        }

        @JvmStatic
        @BeforeAll
        fun setup() {
            BoardManager
        }
    }

    private fun assertMoveSetsAreEquivalent(set1: Set<Pair<Rank, Int>>, set2: Set<Pair<Rank, Int>>) {
        assertEquals(set1.size, set2.size, "Sets have different sizes")
        assertTrue(set1.all { e1 ->
            set2.any { e2 -> areSquaresEquivalent(e1, e2) }
        }, "Not all elements in set1 ($set1) have equivalent elements in set2 ($set2)")
    }

    private fun areSquaresEquivalent(s1: Pair<Rank, Int>, s2: Pair<Rank, Int>): Boolean {
        return s1.first == s2.first && s1.second == s2.second
    }
}