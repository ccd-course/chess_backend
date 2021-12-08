package com.chess.backend.gamemodel.constants;

/**
 * Represents a color of a piece or player.
 * <p>
 * To ensure the right order of each turn, every color has a position.
 * This means, that white always begins and has the first turn, because it has the lowest position value.
 * The color with the highest position value represents the player with the last turn.
 */
public enum Color {
    WHITE("White", 0),
    BLACK("Black", 1),
    RED("Red", 2),
    BLUE("Blue", 3);

    public final String label;
    public final int position;

    Color(String label, int position) {
        this.label = label;
        this.position = position;
    }
}
