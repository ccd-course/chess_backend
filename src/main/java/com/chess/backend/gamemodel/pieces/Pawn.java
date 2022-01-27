package com.chess.backend.gamemodel.pieces;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.abstractmoves.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.services.PlayerService;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a piece.
 */
@Data
public class Pawn extends Piece {
    private  Integer posX;
    private  Integer posY;
    private  Player player;
    private  PieceType type = PieceType.PAWN;
    private boolean motioned;
    private final boolean clockwise; // TODO: 4 of the 8 Pawns move in the other direction. Initialize accordingly.
    private int rank = 2;

    public Pawn(Player player, boolean clockwise) {
        this.player = player;
        this.clockwise = clockwise;
    }

    /**
     * Returns all allowed moves of the piece. The moves for each pieceType are composed of several abstract moves.
     *
     * @param game Game context
     * @return A HashSet of all allowed moves of the piece in this individual game context.
     */
    @Override
    public HashSet<Move> getAllowedFullMoves(ChessGame game) {
        HashSet<Move> allowedMoves = new HashSet<>();
        if (clockwise) {
            allowedMoves.addAll(MoveOneForward.concretise(game, this, false, false, true));
            if (this.getPosY() == PlayerService.getBaseY(player)) {
                allowedMoves.addAll(MoveTwoForward.concretise(game, this, false, false, true));
            }
            allowedMoves.addAll(MovePawnCaptureForward.concretise(game, this, true, false, false));
        } else {
            allowedMoves.addAll(MoveOneBackward.concretise(game, this, false, false, true));
            if (this.getPosY() == PlayerService.getBaseY(player) + 3) {
                allowedMoves.addAll(MoveTwoBackward.concretise(game, this, false, false, true));
            }
            allowedMoves.addAll(MovePawnCaptureBackward.concretise(game, this, true, false, false));
        }
        return allowedMoves;
    }

    /**
     * Converts AllowedFullMoves to an array of Squares representing only the destination of the move.
     *
     * @param game Game context
     * @return ArrayList of possible Squares to move to.
     */
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
        return type;
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
                ", posX=" + posX +
                ", posY=" + posY +
                ", motioned=" + motioned +
                ", clockwise=" + clockwise +
                '}';
    }
}
