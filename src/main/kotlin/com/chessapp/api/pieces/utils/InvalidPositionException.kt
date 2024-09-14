package com.chessapp.api.pieces.utils

/**
 * A basic exception used when someone passes in an
 * out-of-bounds position or a position with an invalid
 * move
 */
class InvalidPositionException(message: String) : Exception("Invalid position: $message")
