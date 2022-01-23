package com.chess.backend.gamemodel.pieces;


import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.abstractmoves.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a piece.
 */
@Data
public class Piece  {
    private transient Integer posX;
    private transient Integer posY;
    private transient Player player;
    private transient PieceType type;
    private transient boolean motioned;
    private transient boolean clockwise; // TODO: 4 of the 8 Pawns move in the other direction. Initialize accordingly.
    private transient int rank;

    public Piece(){
        
    }
    /**
     * Returns all allowed moves of the piece. The moves for each pieceType are composed of several abstract moves.
     *
     * @param game Game context
     * @return A HashSet of all allowed moves of the piece in this individual game context.
     */
    public HashSet<Move> getAllowedFullMoves(ChessGame game) {
        return null;
    }

    /**
     * Converts AllowedFullMoves to an array of Squares representing only the destination of the move.
     *
     * @param game Game context
     * @return ArrayList of possible Squares to move to.
     */
    public ArrayList<Square> getAllowedMoves(ChessGame game) {
        return null;
    }

    public boolean getMainDirection() {
        return this.clockwise;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public Color getColor() {
        return this.player.getColor();
    }

    public String getSymbol() {
        return this.getType().getSymbol();
    }

    public boolean isMotioned() {
        return motioned;
    }

    public void setMotioned(boolean motioned) {
        this.motioned = motioned;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", player=" + player +
                ", motioned=" + motioned +
                ", clockwise=" + clockwise +
                '}';
    }
}
