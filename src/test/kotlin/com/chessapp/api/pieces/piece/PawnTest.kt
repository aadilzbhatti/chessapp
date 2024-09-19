package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File
import com.chessapp.api.pieces.utils.InvalidPositionException
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PawnTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideTestCases")
    fun testStartingPositions(name: String, color: PieceColor, rank: File, file: Int, shouldThrowInvalidPosition: Boolean) {
        val result = runCatching { Pawn(color, rank, file) }
        if (shouldThrowInvalidPosition) {
            assertThrows<InvalidPositionException> { result.getOrThrow() }
        } else {
            result.getOrThrow()
        }
    }

    companion object {
        @JvmStatic
        fun provideTestCases(): Stream<Arguments> {
            return PieceColor.entries.flatMap { color ->
                when (color) {
                    PieceColor.WHITE -> PieceTestUtils.generateTestCases(color, File.entries.toSet(), validFile = 2)
                    PieceColor.BLACK -> PieceTestUtils.generateTestCases(color, File.entries.toSet(), validFile = 7)
                }
            }.stream()
        }
    }
}