package com.chessapp.api.pieces.utils;

/**
 * A basic exception used when someone passes in an
 * out-of-bounds position or a position with an invalid
 * move
 */
public class InvalidPositionException extends Exception {
    public InvalidPositionException(String message) {
        super("Invalid position: " + message);
    }
}
