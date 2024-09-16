package com.chessapp.api.pieces.piece

import com.chessapp.api.board.Rank
import com.chessapp.api.pieces.utils.InvalidPositionException
import java.util.Locale

/**
 * The ChessPiece class is used as a template for creating
 * more specific Chess pieces (Rook, Knight, Bishop, King, Queen, Pawn).
 * Each piece has its own idiosyncrasies and as such must be implemented
 * slightly differently
 */
abstract class ChessPiece(
    private val name: PieceName,
    protected val color: PieceColor,
    protected var rank: Rank,
    protected var file: Int
) {

    fun name() = name
    fun color() = color
    fun rank() = rank
    fun file() = file

    init {
        safeValidateStartingPosition()
    }

    fun getFormattedName(capitalized: Boolean): String {
        return when (name) {
            PieceName.KNIGHT -> "knight"
            PieceName.ROOK -> "rook"
            PieceName.BISHOP -> "bishop"
            PieceName.QUEEN -> "queen"
            PieceName.KING -> "king"
            PieceName.PAWN -> "pawn"
        }.let { pieceName ->
            if (capitalized) pieceName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            } else pieceName.replaceFirstChar {
                it.lowercase(Locale.getDefault())
        } }
    }

    fun setPos(rank: Rank, file: Int) {
        this.rank = rank
        this.file = file
    }

    private fun safeValidateStartingPosition() {
        validateStartingPosition()
    }

    @Throws(InvalidPositionException::class)
    abstract fun validateStartingPosition()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChessPiece) return false

        return this.name == other.name
                && this.color == other.color
                && this.rank == other.rank
                && this.file == other.file
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + rank.hashCode()
        result = 31 * result + file
        return result
    }
}


