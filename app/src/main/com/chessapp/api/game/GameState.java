package com.chessapp.api.game;

/**
 * Simple enum so we don't have to deal with
 * strings when doing game condition checking.
 */
public enum GameState {
    CHECK,
    CHECKMATE,
    STALEMATE,
    NOTHING
}