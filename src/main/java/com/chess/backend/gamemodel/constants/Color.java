package com.chess.backend.gamemodel.constants;

public enum Color {
    WHITE("White"),
    BLACK("Black"),
    RED("Red"),
    BLUE("Blue");

    public final String label;

    Color(String label) {
        this.label = label;
    }
}
