package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import org.junit.jupiter.params.provider.Arguments

object PieceTestUtils {
    fun generateTestCases(color: PieceColor, validRanks: Set<Rank>, validFile: Int): List<Arguments> {
        return Rank.entries.flatMap { rank ->
            (1..8).map { file ->
                val correctRank = validRanks.contains(rank)
                val correctFile = file == validFile
                val description = when {
                    correctRank && correctFile -> "$color: Correct rank($rank) and file($file)"
                    correctRank -> "$color: Correct rank($rank) and incorrect file($file)"
                    correctFile -> "$color: Incorrect rank($rank) and correct file($file)"
                    else -> "$color: Incorrect rank($rank) and incorrect file($file)"
                }
                Arguments.of(description, color, rank, file, !(correctRank && correctFile))
            }
        }
    }
}