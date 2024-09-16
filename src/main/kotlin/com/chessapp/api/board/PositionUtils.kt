package com.chessapp.api.board

object PositionUtils {
    fun getCoordinatesFromRankFile(rank: Rank, file: Int): Pair<Int, Int> {
        require(file in 1..8) { "Invalid file with value $file: must be between 1 and 8" }
        val xPos = rank.ordinal
        val yPos = file - 1
        return xPos to yPos
    }

    fun getRankFileFromCoordinates(xPos: Int, yPos: Int): Pair<Rank, Int> {
        require(xPos in 0..7) { "Invalid x-position given: x-positions must be between 0 and 7, got: $xPos" }
        require(yPos in 0..7) { "Invalid y-position given: y-positions must be between 0 and 7, got: $yPos" }
        val rank = Rank.entries[xPos]
        val file = yPos + 1
        return rank to file
    }

    fun validCoordinatesFilter(coords: Pair<Int, Int>): Boolean {
        return coords.first in (0..7) && coords.second in (0..7)
    }
}

enum class Rank {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H
}
