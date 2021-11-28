package com.chess.backend.gamemodel.contants;

public enum Color {
    BLACK("Black"),
    WHITE("White"),
    RED("Red"),
    BLUE("Blue");

    public final String label;

    Color(String label) {
        this.label = label;
    }
}
