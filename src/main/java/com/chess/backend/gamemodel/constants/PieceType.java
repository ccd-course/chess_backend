package com.chess.backend.gamemodel.constants;

public enum PieceType {
    PAWN("Pawn", ""),
    ROOK("Rook", "R"),
    KNIGHT("Knight", "N"),
    BISHOP("Bishop", "B"),
    QUEEN("Queen", "Q"),
    KING("King", "K");

    public final String label;
    public final String symbol;

    PieceType(String label, String symbol) {
        this.label = label;
        this.symbol = symbol;
    }

    public String getLabel() {
        return label;
    }
}
