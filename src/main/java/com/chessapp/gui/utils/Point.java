package com.chessapp.gui.utils;

/**
 * Simple class used to abstract positions for help
 * in linking API to GUI
 */
public class Point {
    private final int x;
    private final int y;

    public Point(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
