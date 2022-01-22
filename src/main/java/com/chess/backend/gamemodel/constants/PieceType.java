package com.chess.backend.gamemodel.constants;

/**
 * Represents all possible piece types and their respective symbols.
 */
public enum PieceType {
    PAWN("Pawn", ""),
    ROOK("Rook", "R"),
    KNIGHT("Knight", "N"),
    BISHOP("Bishop", "B"),
    QUEEN("Queen", "Q"),
    KING("King", "K"),
    CANNON("Cannon", "C");

    private final String label;
    private final String symbol;

    PieceType(String label, String symbol) {
        this.label = label;
        this.symbol = symbol;
    }

    public String getLabel() {
        return label;
    }

    public String getSymbol() {
        return symbol;
    }
}
