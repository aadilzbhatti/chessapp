package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import com.chessapp.api.pieces.utils.InvalidPositionException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RookTest {
    @Test
    fun testValidStartingPosition() {
        Rook(PieceColor.WHITE, Rank.A, 1)
        Rook(PieceColor.WHITE, Rank.H, 1)
        Rook(PieceColor.BLACK, Rank.A, 8)
        Rook(PieceColor.BLACK, Rank.H, 8)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTestCases")
    fun testStartingPositions(name: String, color: PieceColor, rank: Rank, file: Int, shouldThrowInvalidPosition: Boolean) {
        val result = runCatching { Rook(color, rank, file) }
        if (shouldThrowInvalidPosition) {
            assertThrows<InvalidPositionException> { result.getOrThrow() }
        } else {
            result.getOrThrow()
        }
    }

    companion object {
        @JvmStatic
        fun provideTestCases(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("White: Correct rank (A), correct file", PieceColor.WHITE, Rank.A, 1, false),
                Arguments.of("White: Correct rank (H), correct file", PieceColor.WHITE, Rank.H, 1, false),
                Arguments.of("Black: Correct rank (A), correct file", PieceColor.BLACK, Rank.A, 8, false),
                Arguments.of("Black: Correct rank (H), correct file", PieceColor.BLACK, Rank.H, 8, false),
                Arguments.of("White: Incorrect rank, correct file", PieceColor.WHITE, Rank.F, 1, true),
                Arguments.of("White: Correct rank, incorrect file", PieceColor.WHITE, Rank.A, 2, true),
                Arguments.of("White: Incorrect rank, incorrect file", PieceColor.WHITE, Rank.F, 2, true),
                Arguments.of("Black: Incorrect rank, correct file", PieceColor.BLACK, Rank.F, 8, true),
                Arguments.of("Black: Correct rank, incorrect file", PieceColor.BLACK, Rank.A, 2, true),
                Arguments.of("Black: Incorrect rank, incorrect file", PieceColor.BLACK, Rank.F, 2, true),
            )
        }
    }
}