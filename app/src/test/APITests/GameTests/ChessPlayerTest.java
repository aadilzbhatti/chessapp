package APITests.GameTests;

import ChessAPI.Game.BoardPosition;
import com.chessapp.api.game.ChessPlayer;
import com.chessapp.api.pieces.piece.Pawn;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for the various Chess Player abilities
 */
public class ChessPlayerTest {
    BoardPosition position = new BoardPosition();

    /**
     * Tests that we can make Chess players
     */
    @Test
    public void shouldBeAbleToInitialize() {
        ChessPlayer p = new ChessPlayer(ChessPieceColor.RED, position);
        Assert.assertNotNull(p);
    }

    /**
     * Tests that we can initialize chess players
     * and have all their pieces initialized
     */
    @Test
    public void shouldHaveAllPiecesAndList() {
        ChessPlayer p = new ChessPlayer(ChessPieceColor.RED, position);
        Assert.assertNotNull(p.king);
        Assert.assertNotNull(p.queen);
        Assert.assertNotNull(p.leftBishop);
        Assert.assertNotNull(p.rightBishop);
        Assert.assertNotNull(p.leftKnight);
        Assert.assertNotNull(p.rightKnight);
        Assert.assertNotNull(p.leftRook);
        Assert.assertNotNull(p.rightRook);
        Assert.assertNotNull(p.pawns);
        for (Pawn pawn : p.pawns) {
            Assert.assertNotNull(pawn);
        }
    }

    /**
     * Tests that we can attack other players
     * @throws Exception
     */
    @Test
    public void beAbleToAttackOtherPlayers() throws Exception {
        ChessPlayer p1 = new ChessPlayer(ChessPieceColor.RED, position);
        ChessPlayer p2 = new ChessPlayer(ChessPieceColor.WHITE, position);
        p1.leftKnight.move(2, 2, position);
        p2.rightKnight.move(5, 5, position);
        p1.leftKnight.move(4, 3, position);
        p2.attack(p1.leftKnight, 4, 3, position, p1);
        Assert.assertTrue(p2.takenPieces.contains(p1.leftKnight));
        Assert.assertFalse(p1.pieces.contains(p1.leftKnight));
    }
}
