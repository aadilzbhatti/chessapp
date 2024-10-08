package com.chessapp.api.pieces.piece

import com.chessapp.api.board.File
import org.junit.jupiter.params.provider.Arguments

object PieceTestUtils {
    fun generateTestCases(color: PieceColor, validFiles: Set<File>, validFile: Int): List<Arguments> {
        return File.entries.flatMap { rank ->
            (1..8).map { file ->
                val correctRank = validFiles.contains(rank)
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