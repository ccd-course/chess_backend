package com.chess.backend.gamemodel.pieces;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.abstractmoves.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
public class Wazir extends Piece {
    private Integer posX;
    private Integer posY;
    private Player player;
    private PieceType type = PieceType.WAZIR;
    private boolean motioned;
    private final boolean clockwise;

    public Wazir(Player player, boolean clockwise) {
        this.player = player;
        this.clockwise = clockwise;
    }

    @Override
    public HashSet<Move> getAllowedFullMoves(ChessGame game) {
        HashSet<Move> allowedMoves = new HashSet<>();
        allowedMoves.addAll(MoveOneForward.concretise(game, this, true, false, true));
        allowedMoves.addAll(MoveOneBackward.concretise(game, this, true, false, true));
        allowedMoves.addAll(MoveOneLeft.concretise(game, this, true, false, true));
        allowedMoves.addAll(MoveOneRight.concretise(game, this, true, false, true));
        return allowedMoves;
    }

    @Override
    public ArrayList<Square> getAllowedMoves(ChessGame game) {
        Set<Move> allowedFullMoves = getAllowedFullMoves(game);
        ArrayList<Square> allowedMoves = new ArrayList<>();

        for (Move move :
                allowedFullMoves) {
            allowedMoves.add(move.getTo());
        }

        return allowedMoves;
    }

    @Override
    public boolean getMainDirection() {
        return this.clockwise;
    }

    @Override
    public PieceType getType() {
        return this.type;
    }

    @Override
    public void setType(PieceType type) {
        this.type = type;
    }

    @Override
    public Color getColor() {
        return this.player.getColor();
    }

    @Override
    public String getSymbol() {
        return this.getType().getSymbol();
    }

    @Override
    public boolean isMotioned() {
        return motioned;
    }

    @Override
    public void setMotioned(boolean motioned) {
        this.motioned = motioned;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isClockwise() {
        return clockwise;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", player=" + player +
                ", motioned=" + motioned +
                ", clockwise=" + clockwise +
                '}';
    }
}
