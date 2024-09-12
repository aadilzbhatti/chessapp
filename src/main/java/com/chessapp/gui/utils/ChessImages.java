package com.chessapp.gui.utils;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Interface for retrieving Chess piece images
 */
public class ChessImages {
    public Image whiteRook;
    public Image redRook;
    public Image whiteKnight;
    public Image redKnight;
    public Image whiteBishop;
    public Image redBishop;
    public Image whiteKing;
    public Image redKing;
    public Image whiteQueen;
    public Image redQueen;
    public Image whitePawn;
    public Image redPawn;

    public ArrayList<Image> images;

    public ChessImages() {
        whiteRook = new Image(getClass().getResourceAsStream("assets/white_rook.png"), 80, 80, false, false);
        redRook = new Image(getClass().getResourceAsStream("assets/red_rook.png"), 80, 80, false, false);
        whiteKnight = new Image(getClass().getResourceAsStream("assets/white_knight.png"), 80, 80, false, false);
        redKnight = new Image(getClass().getResourceAsStream("assets/red_knight.png"), 80, 80, false, false);
        whiteBishop = new Image(getClass().getResourceAsStream("assets/white_bishop.png"), 80, 80, false, false);
        redBishop = new Image(getClass().getResourceAsStream("assets/red_bishop.png"), 80, 80, false, false);
        whiteKing = new Image(getClass().getResourceAsStream("assets/white_king.png"), 80, 80, false, false);
        redKing = new Image(getClass().getResourceAsStream("assets/red_king.png"), 80, 80, false, false);
        whiteQueen = new Image(getClass().getResourceAsStream("assets/white_queen.png"), 80, 80, false, false);
        redQueen = new Image(getClass().getResourceAsStream("assets/red_queen.png"), 80, 80, false, false);
        whitePawn = new Image(getClass().getResourceAsStream("assets/white_pawn.png"), 80, 80, false, false);
        redPawn = new Image(getClass().getResourceAsStream("assets/red_pawn.png"), 80, 80, false, false);

        images = new ArrayList<Image>() {{
            add(whiteRook);
            add(redRook);
            add(whiteKnight);
            add(redKnight);
            add(whiteBishop);
            add(redBishop);
            add(whiteKing);
            add(redKing);
            add(whiteQueen);
            add(redQueen);
            add(whitePawn);
            add(redPawn);
        }};
    }
}
