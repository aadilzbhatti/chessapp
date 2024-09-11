package GUITests;

import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.gui.APILinker;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Used to test all our nasty static functions in the GUI and API linker
 */
public class UtilityTests {
    APILinker game;

    @Before
    public void setUp() {
        game = new APILinker();
    }

    @Test
    public void canInitializeTheLinker() {
        Assert.assertNotNull(game);
        Assert.assertEquals(ChessPieceColor.WHITE, game.currentTurn());
    }

    @Test
    public void canChangeTurn() {
        Assert.assertEquals(ChessPieceColor.WHITE, game.currentTurn());
        game.changeTurn();
        Assert.assertEquals(ChessPieceColor.RED, game.currentTurn());
    }
}
