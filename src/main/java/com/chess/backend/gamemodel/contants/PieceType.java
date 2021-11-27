package com.chess.backend.gamemodel.contants;

public enum PieceType {
    PAWN("Pawn"),
    ROOK("Rook"),
    KNIGHT("Knight"),
    BISHOP("Bishop"),
    QUEEN("Queen"),
    KING("King");

    public final String label;

    PieceType(String label) {
        this.label = label;
    }
}
