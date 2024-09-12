package com.chessapp;

import com.chessapp.gui.ChessBoard;
import javafx.application.Application;

/**
 * The official main method, used to start a
 * game of Chess.
 */


public class ChessApp {
    public static void main(String[] args) {
        Application.launch(ChessBoard.class);
    }
}

// TODO add stalemate conditions!
// TODO fix images to be solid white
