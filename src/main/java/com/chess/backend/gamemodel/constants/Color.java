package com.chess.backend.gamemodel.constants;

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
