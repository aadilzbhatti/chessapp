package com.chessapp.api.board

import com.chessapp.api.pieces.piece.ChessPiece
import com.chessapp.api.pieces.piece.DefaultPieces
import com.chessapp.api.pieces.piece.Pawn
import com.chessapp.api.pieces.piece.PieceColor
import com.chessapp.api.pieces.utils.InvalidPositionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LegalMoveCalculatorTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideStartingPositionTestCases")
    fun testLegalMovesStartingPosition(name: String, piece: ChessPiece, expectedLegalMoves: Set<Pair<File, Int>>) {
        val legalMoveCalculator = LegalMoveCalculator(BoardManager.boardPosition())
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, piece, expectedLegalMoves)
    }

    @Test
    fun testLegalMovesRookInCenter() {
        val boardPosition = BoardPosition() // empty board position
        val legalMoveCalculator = LegalMoveCalculator(boardPosition)

        val rook = DefaultPieces.whiteQueensideRook()
        boardPosition.occupyPosition(rook)

        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, rook, setOf(
            File.B to 1,
            File.C to 1,
            File.D to 1,
            File.E to 1,
            File.F to 1,
            File.G to 1,
            File.H to 1,
            File.A to 2,
            File.A to 3,
            File.A to 4,
            File.A to 5,
            File.A to 6,
            File.A to 7,
            File.A to 8
        ))

        // move the rook to the center, yes we're not moving it correctly, but we're just testing the legal moves :)
        boardPosition.movePiece(rook, File.E, 5)
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, rook, setOf(
            File.F to 5,
            File.G to 5,
            File.H to 5,
            File.A to 5,
            File.B to 5,
            File.C to 5,
            File.D to 5,
            File.E to 6,
            File.E to 7,
            File.E to 8,
            File.E to 1,
            File.E to 2,
            File.E to 3,
            File.E to 4
        ))

        // add other pieces around the rook so that it is blocked
        val knight = DefaultPieces.whiteKingsideKnight()
        val pawn = DefaultPieces.whitePawns()[1]
        val bishop = DefaultPieces.whiteQueensideBishop()
        val queen = DefaultPieces.whiteQueen()

        boardPosition.occupyPosition(knight)
        boardPosition.occupyPosition(pawn)
        boardPosition.occupyPosition(bishop)
        boardPosition.occupyPosition(queen)

        boardPosition.movePiece(knight, File.E, 7)
        boardPosition.movePiece(pawn, File.G, 5)
        boardPosition.movePiece(bishop, File.C, 5)
        boardPosition.movePiece(queen, File.E, 3)
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, rook, setOf(
            File.E to 6,
            File.E to 4,
            File.D to 5,
            File.F to 5
        ))

        boardPosition.movePiece(knight, File.E, 6)
        boardPosition.movePiece(pawn, File.F, 5)
        boardPosition.movePiece(bishop, File.D, 5)
        boardPosition.movePiece(queen, File.E, 4)
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, rook, emptySet())
    }

    @Test
    fun testLegalMovesKnightInCenter() {
        val boardPosition = BoardPosition() // empty board position
        val legalMoveCalculator = LegalMoveCalculator(boardPosition)

        val knight = DefaultPieces.whiteQueensideKnight()
        boardPosition.occupyPosition(knight)
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, knight, setOf(File.A to 3, File.C to 3, File.D to 2))

        // not a valid move but we're just testing legal moves
        // test that we can move to all 8 knight squares
        boardPosition.movePiece(knight, File.E, 5)
        val knightMoves = listOf(
            File.C to 6,
            File.D to 7,
            File.F to 7,
            File.G to 6,
            File.C to 4,
            File.D to 3,
            File.F to 3,
            File.G to 4
        )
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, knight, knightMoves.toSet())

        // test that when some squares are occupied by same-color pieces, we cannot move to those squares
        val pawns = File.entries.map { file ->
            Pawn(PieceColor.WHITE, file, 2).also { boardPosition.occupyPosition(it) }
        }
        for (i in 0 until 4) {
            boardPosition.movePiece(pawns[i], knightMoves[i].first, knightMoves[i].second)
        }
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, knight, knightMoves.subList(4, knightMoves.size).toSet())

        // test that once all squares are occupied by same-color pieces, we cannot move to any of them
        for (i in 4 until pawns.size) {
            boardPosition.movePiece(pawns[i], knightMoves[i].first, knightMoves[i].second)
        }
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, knight, emptySet())

        // test that we can move to squares with opposite color pieces
        pawns.forEach { boardPosition.removePiece(it) }
        val blackPawns = File.entries.map { file ->
            Pawn(PieceColor.BLACK, file, 7).also { boardPosition.occupyPosition(it) }
        }
        for (i in 0 until 4) {
            try {
                boardPosition.movePiece(blackPawns[i], knightMoves[i].first, knightMoves[i].second)
            } catch (e: InvalidPositionException) {
                continue // we are already in position, can move on
            }
        }
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, knight, knightMoves.toSet())
    }

    @Test
    fun testLegalMovesQueenInCenter() {
        val boardPosition = BoardPosition()
        val legalMoveCalculator = LegalMoveCalculator(boardPosition)

        val queen = DefaultPieces.whiteQueen()
        boardPosition.occupyPosition(queen)

        // test the queen in the center with no blocking pieces
        boardPosition.movePiece(queen, File.E, 5)
        val queenMoves = listOf(
            File.A to 1,
            File.A to 5,
            File.B to 2,
            File.B to 5,
            File.B to 8,
            File.C to 3,
            File.C to 5,
            File.C to 7,
            File.D to 4,
            File.D to 5,
            File.D to 6,
            File.E to 1,
            File.E to 2,
            File.E to 3,
            File.E to 4,
            File.E to 6,
            File.E to 7,
            File.E to 8,
            File.F to 4,
            File.F to 5,
            File.F to 6,
            File.G to 3,
            File.G to 5,
            File.G to 7,
            File.H to 2,
            File.H to 5,
            File.H to 8
        )
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, queen, queenMoves.toSet())

        // test the queen in the center with a few blocking pieces
        val rook = DefaultPieces.whiteQueensideRook().also { boardPosition.occupyPosition(it) }
        val pawn = DefaultPieces.whitePawns()[0].also { boardPosition.occupyPosition(it) }
        val bishop = DefaultPieces.whiteQueensideBishop().also { boardPosition.occupyPosition(it) }
        val king = DefaultPieces.whiteKing().also { boardPosition.occupyPosition(it) }
        val knight = DefaultPieces.whiteQueensideKnight().also { boardPosition.occupyPosition(it) }

        boardPosition.movePiece(rook, File.E, 7)
        boardPosition.movePiece(pawn, File.C, 7)
        boardPosition.movePiece(bishop, File.F, 5)
        boardPosition.movePiece(king, File.D, 4)
        boardPosition.movePiece(knight, File.E, 1)

        val occupiedSquares = setOf(File.E to 7, File.C to 7, File.F to 5, File.D to 4, File.E to 1)
        val blockedSquares = setOf(File.A to 1, File.B to 2, File.C to 3, File.B to 8, File.E to 8, File.G to 5, File.H to 5)
        val whiteBlockingQueenMoves = queenMoves.subtract(occupiedSquares union blockedSquares)
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, queen, whiteBlockingQueenMoves.toSet())
    }

    @Test
    fun testLegalMovesKingInCenter() {
        val boardPosition = BoardPosition()
        val legalMoveCalculator = LegalMoveCalculator(boardPosition)

        val king = DefaultPieces.whiteKing().also { boardPosition.occupyPosition(it) }

        // test king in the center with no blocking pieces
        boardPosition.movePiece(king, File.E, 5)
        val kingMoves = setOf(
            File.D to 4,
            File.D to 5,
            File.D to 6,
            File.E to 4,
            File.E to 6,
            File.F to 4,
            File.F to 5,
            File.F to 6,
        )
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, king, kingMoves)

        // test king in the center with a few blocking pieces
        val rook = DefaultPieces.whiteQueensideRook().also { boardPosition.occupyPosition(it) }
        val pawn = DefaultPieces.whitePawns()[0].also { boardPosition.occupyPosition(it) }
        val knight = DefaultPieces.whiteQueensideKnight().also { boardPosition.occupyPosition(it) }
        val queen = DefaultPieces.whiteQueen().also { boardPosition.occupyPosition(it) }

        boardPosition.movePiece(rook, File.E, 6)
        boardPosition.movePiece(pawn, File.F, 4)
        boardPosition.movePiece(knight, File.F, 5)
        boardPosition.movePiece(queen, File.D, 4)
        getLegalMovesAndAssertEqualsExpectedMoveSet(legalMoveCalculator, king, kingMoves.subtract(setOf(
            File.E to 6,
            File.F to 4,
            File.F to 5,
            File.D to 4
        )))
    }

    private fun getLegalMovesAndAssertEqualsExpectedMoveSet(
        legalMoveCalculator: LegalMoveCalculator,
        piece: ChessPiece,
        expectedLegalMoves: Set<Pair<File, Int>>
    ) {
        val gotLegalMoves = legalMoveCalculator.getLegalMovesForPiece(piece)
        assertNotNull(gotLegalMoves)
        gotLegalMoves?.let { assertMoveSetsAreEquivalent(expectedLegalMoves, gotLegalMoves) }
    }

    companion object {
        @JvmStatic
        fun provideStartingPositionTestCases(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("White queenside rook", DefaultPieces.whiteQueensideRook(), emptySet<Pair<File, Int>>()),
                Arguments.of("White queenside knight", DefaultPieces.whiteQueensideKnight(), setOf(File.A to 3, File.C to 3)),
                Arguments.of("White queenside bishop", DefaultPieces.whiteQueensideBishop(), emptySet<Pair<File, Int>>()),
                Arguments.of("White queen", DefaultPieces.whiteQueen(), emptySet<Pair<File, Int>>()),
                Arguments.of("White king", DefaultPieces.whiteKing(), emptySet<Pair<File, Int>>()),
                Arguments.of("White kingside bishop", DefaultPieces.whiteKingsideBishop(), emptySet<Pair<File, Int>>()),
                Arguments.of("White kingside knight", DefaultPieces.whiteKingsideKnight(), setOf(File.F to 3, File.H to 3)),
                Arguments.of("White kingside rook", DefaultPieces.whiteKingsideRook(), emptySet<Pair<File, Int>>()),
                Arguments.of("Black queenside rook", DefaultPieces.blackQueensideRook(), emptySet<Pair<File, Int>>()),
                Arguments.of("Black queenside knight", DefaultPieces.blackQueensideKnight(), setOf(File.A to 6, File.C to 6)),
                Arguments.of("Black queenside bishop", DefaultPieces.blackQueensideBishop(), emptySet<Pair<File, Int>>()),
                Arguments.of("Black queen", DefaultPieces.blackQueen(), emptySet<Pair<File, Int>>()),
                Arguments.of("Black king", DefaultPieces.blackKing(), emptySet<Pair<File, Int>>()),
                Arguments.of("Black kingside bishop", DefaultPieces.blackKingsideBishop(), emptySet<Pair<File, Int>>()),
                Arguments.of("Black kingside knight", DefaultPieces.blackKingsideKnight(), setOf(File.F to 6, File.H to 6)),
                Arguments.of("Black kingside rook", DefaultPieces.blackKingsideRook(), emptySet<Pair<File, Int>>()),
                )
        }

        @JvmStatic
        @BeforeAll
        fun setup() {
            BoardManager
        }
    }

    private fun assertMoveSetsAreEquivalent(set1: Set<Pair<File, Int>>, set2: Set<Pair<File, Int>>) {
        assertEquals(set1.size, set2.size, "Sets have different sizes")
        assertTrue(set1.all { e1 ->
            set2.any { e2 -> areSquaresEquivalent(e1, e2) }
        }, "Not all elements in set1 ($set1) have equivalent elements in set2 ($set2)")
    }

    private fun areSquaresEquivalent(s1: Pair<File, Int>, s2: Pair<File, Int>): Boolean {
        return s1.first == s2.first && s1.second == s2.second
    }
}