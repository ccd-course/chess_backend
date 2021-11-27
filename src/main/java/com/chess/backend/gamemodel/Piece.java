package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.abstractmoves.MoveDiagonal;
import com.chess.backend.gamemodel.contants.Color;
import com.chess.backend.gamemodel.contants.PieceType;

import java.util.HashSet;
import java.util.Set;

public class Piece {
    private PieceType type;
    private Color color;
    Set<AbstractMove> absctractMoves;

    public Piece(PieceType type, Color color, Set<AbstractMove> absctractMoves) {
        this.type = type;
        this.color = color;
        this.absctractMoves = absctractMoves;
    }

    public Set<Move> getAllowedMoves(Game game){
        Set<Move> allowedMoves = new HashSet<>();

        switch (this.type){
            case PAWN:
                allowedMoves.addAll(new MoveDiagonal(game).concretise());
        }

        return allowedMoves;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Set<AbstractMove> getAbsctractMoves() {
        return absctractMoves;
    }

    public void setAbsctractMoves(Set<AbstractMove> absctractMoves) {
        this.absctractMoves = absctractMoves;
    }
}
