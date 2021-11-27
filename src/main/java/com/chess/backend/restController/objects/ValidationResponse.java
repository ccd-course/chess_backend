package com.chess.backend.restController.objects;

/**
 * Represents the validation state of a chosen move.
 *
 * States that can be used: <br>
 * {@link VALID_MOVE} - indicates that the move is valid <br>
 * {@link NO_VALID_MOVE} - indicates that the move is not valid
 *
 * @author Hannes Stuetzer
 */
public enum ValidationResponse {
    /**
     * The chosen move is valid.
     */
    VALID_MOVE,
    /**
     * The chosen move is invalid.
     */
    NO_VALID_MOVE
}
