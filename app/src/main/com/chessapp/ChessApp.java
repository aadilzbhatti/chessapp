package com.chessapp;

/**
 * The official main method, used to start a
 * game of Chess.
 */

import ChessGUI.ChessBoard;

public class ChessApp {
    public static void main(String[] args) {
        Application.launch(ChessBoard.class);
    }
}

// TODO add stalemate conditions!
// TODO fix images to be solid white
