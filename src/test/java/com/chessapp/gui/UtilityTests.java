package com.chessapp.gui;

import com.chessapp.api.pieces.utils.ChessPieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Used to test all our nasty static functions in the GUI and API linker
 */
public class UtilityTests {
    APILinker game;

    @BeforeEach
    public void setUp() {
        game = new APILinker();
    }

    @Test
    public void canInitializeTheLinker() {
        assertNotNull(game);
        assertEquals(ChessPieceColor.WHITE, game.currentTurn());
    }

    @Test
    public void canChangeTurn() {
        assertEquals(ChessPieceColor.WHITE, game.currentTurn());
        game.changeTurn();
        assertEquals(ChessPieceColor.RED, game.currentTurn());
    }
}
