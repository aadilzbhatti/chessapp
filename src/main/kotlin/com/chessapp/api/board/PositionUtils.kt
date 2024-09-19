package com.chessapp.api.board

import com.chessapp.BOARD_SIZE

object PositionUtils {
    fun getCoordinatesFromFileRank(file: File, rank: Int): Pair<Int, Int> {
        require(rank in 1..8) { "Invalid file with value $rank: must be between 1 and 8" }
        val xPos = file.ordinal
        val yPos = rank - 1
        return xPos to yPos
    }

    fun getFileRankFromCoordinates(xPos: Int, yPos: Int): Pair<File, Int> {
        require(xPos in 0 until BOARD_SIZE) { "Invalid x-position given: x-positions must be between 0 and 7, got: $xPos" }
        require(yPos in 0 until BOARD_SIZE) { "Invalid y-position given: y-positions must be between 0 and 7, got: $yPos" }
        val rank = File.entries[xPos]
        val file = yPos + 1
        return rank to file
    }

    fun isValidCoordinates(coords: Pair<Int, Int>): Boolean {
        return coords.first in (0 until BOARD_SIZE) && coords.second in (0 until BOARD_SIZE)
    }
}

enum class File {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H
}
