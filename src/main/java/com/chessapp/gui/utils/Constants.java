package com.chessapp.gui.utils;

/**
 * Helper class for GUI constants so we don't have to keep calculating things
 * within the GUI creation
 */
public class Constants {

    /**
     * The board size and offset. Board size given in pixels as
     * 640 to allow for 8 pieces of size 80x80 pixels each, and
     * have each square be 80x80 pixels. The OFFSET is the size
     * of each square side length and piece side length, called
     * offset due to its use in offsetting some x,y positions in
     * board setup.
     */
    public static final int BOARD_SIZE_IN_PIXELS = 640;
    public static final int OFFSET = BOARD_SIZE_IN_PIXELS / 8;

    /**
     * The x-positions for each piece. Red/White pieces have the
     * same x-position essentially depending on the side of the board.
     */
    public static final double LEFT_ROOK_X_POS = -BOARD_SIZE_IN_PIXELS / 2 + OFFSET / 2;
    public static final double RIGHT_ROOK_X_POS = BOARD_SIZE_IN_PIXELS / 2 - OFFSET / 2;
    public static final double LEFT_KNIGHT_X_POS = -BOARD_SIZE_IN_PIXELS / 2 + 3 * OFFSET / 2;
    public static final double RIGHT_KNIGHT_X_POS = BOARD_SIZE_IN_PIXELS / 2 - 3 * OFFSET / 2;
    public static final double LEFT_BISHOP_X_POS = -BOARD_SIZE_IN_PIXELS / 2 + 5 * OFFSET / 2;
    public static final double RIGHT_BISHOP_X_POS = BOARD_SIZE_IN_PIXELS / 2 - 5 * OFFSET / 2;
    public static final double QUEEN_X_POS = -BOARD_SIZE_IN_PIXELS / 2 + 7 * OFFSET / 2;
    public static final double KING_X_POS = BOARD_SIZE_IN_PIXELS / 2 - 7 * OFFSET / 2;

    /**
     * The y-positions for each piece. Pawns are slightly different since they
     * lie above/below the main pieces.
     */
    public static final double RED_Y_POS = -BOARD_SIZE_IN_PIXELS / 2 + OFFSET / 2;
    public static final double WHITE_Y_POS = BOARD_SIZE_IN_PIXELS / 2 - OFFSET / 2;
    public static final double RED_PAWN_Y_POS = -BOARD_SIZE_IN_PIXELS / 2 + 3 * OFFSET / 2;
    public static final double WHITE_PAWN_Y_POS = BOARD_SIZE_IN_PIXELS / 2 - 3 * OFFSET / 2;
}
