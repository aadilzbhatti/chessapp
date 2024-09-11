package com.chessapp.api.game;

import com.chessapp.api.pieces.piece.ChessPiece;
import com.chessapp.api.pieces.utils.ChessPieceColor;

import java.util.Scanner;

/**
 * The Chess API interface, used to link up pieces
 * and Chess players. Will later link with GUI
 */

public class ChessGame {

    // The player using the color "red"
    ChessPlayer redPlayer;

    // The player using the color "white"
    ChessPlayer whitePlayer;

    // The board position manager
    BoardPosition position;

    /**
     * The default constructor for a chess game.
     * Initializes the game to be between two players of
     * color RED and WHITE
     */
    public ChessGame(int boardSize) {
        position = new BoardPosition(boardSize);
        redPlayer = new ChessPlayer(ChessPieceColor.RED, position);
        whitePlayer = new ChessPlayer(ChessPieceColor.WHITE, position);
    }

    /**
     * The "main" method of this library. Plays a (simplified) game of
     * chess between the two players.
     */
    public void play() {
        System.out.println("Welcome to Chess! Which player would you like to be (Red or White) ?");

        Scanner scanner = new Scanner(System.in);

        while(true) {
            String color = scanner.next();
            if (color.equalsIgnoreCase("RED")) {
                System.out.println("You have selected Red! Then the other player must be white.");
                break;
            } else if (color.equalsIgnoreCase("WHITE")) {
                System.out.println("You have selected White! Then the other player must be red.");
                break;
            } else {
                System.out.println("Try again!");
            }
        }

        while(true) {
            displayHelp();

            // White player turn
            takeTurn(scanner, "White player", whitePlayer);

            // Red player turn
            takeTurn(scanner, "Red player", redPlayer);
        }
    }

    /**
     * An internal method used to wrap the game logic in player turns
     * @param scanner The Scanner object used to grab input from the user
     * @param playerName The "name" of the player (the color)
     * @param player The player object who is taking their turn
     */
    void takeTurn(Scanner scanner, String playerName, ChessPlayer player) {
        System.out.println(playerName + "'s turn! Which piece would you like to move?");

        ChessPiece piece;
        while (true) {
            String input = scanner.next();
            if ((piece = parsePieceFromInput(player, input)) != null) {
                break;
            }
        }

        System.out.println("Where would you like to move it? Your current position is: " +
                "(" + piece.x() + ", " + piece.y() + ")");

        while (true) {
            System.out.println("Enter the x-position");
            int x = scanner.nextInt();
            System.out.println("Enter the y-position");
            int y = scanner.nextInt();
            try {
                piece.move(x, y, position);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A "usage" method of sorts, used to help players between turns.
     */
    private void displayHelp() {
        System.out.println("\nTo choose a piece, type in a keyword to match the piece and the side, if necessary.\n" +
                "So for example, if you want to move the left Knight, you would type in \'LKnight\'\n" +
                "For pawns, type in \'p3\' to move the third pawn from the left. See below for help.");

        System.out.println("Left/Right Rook: (L/R)Rook\nLeft/Right Knight: (L/R)Knight\n" +
                "Left/Right Bishop: (L/R)Bishop\nKing: King\nQueen: Queen\n1st-8th Pawn: p(1-8)\n\n");
    }

    /**
     * Takes the input from the user and figures out which piece needs to be played
     * @param player The player taking the turn
     * @param input The user-input from the player
     * @return The chosen chess piece
     */
    private ChessPiece parsePieceFromInput(ChessPlayer player, String input) {
        if (input.equalsIgnoreCase("LRook")) {
            return player.leftRook;
        } else if (input.equalsIgnoreCase("RRook")) {
            return player.rightRook;
        } else if (input.equalsIgnoreCase("LKnight")) {
            return player.leftKnight;
        } else if (input.equalsIgnoreCase("RKnight")) {
            return player.rightKnight;
        } else if (input.equalsIgnoreCase("LBishop")) {
            return player.leftBishop;
        } else if (input.equalsIgnoreCase("RBishop")) {
            return player.rightBishop;
        } else if (input.equalsIgnoreCase("King")) {
            return player.king;
        } else if (input.equalsIgnoreCase("Queen")) {
            return player.queen;
        } else if (input.charAt(0) == 'p' && Character.isDigit(input.charAt(1))) {
            int pawn = Character.getNumericValue(input.charAt(1));
            if (pawn < 1) return null;
            return player.pawns[pawn - 1];
        } else {
            System.out.println("Invalid piece given. Try another selection.");
            return null;
        }
    }
}
