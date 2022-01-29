package com.chess.backend.gamemodel;


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
public class Piece  {
    private transient Integer posX;
    private transient Integer posY;
    private transient Player player;
    private transient PieceType type;
    private transient boolean motioned;
    private transient boolean clockwise; // TODO: 4 of the 8 Pawns move in the other direction. Initialize accordingly.
    private transient int rank;

    public Piece(PieceType pieceType, Player player, boolean clockwise) {
        this.type = pieceType;
        this.player = player;
        this.clockwise = clockwise;
    }
    /**
     * Returns all allowed moves of the piece. The moves for each pieceType are composed of several abstract moves.
     *
     * @param chessboard Chessboard
     * @return A HashSet of all allowed moves of the piece in this individual game context.
     */
    public HashSet<Move> getAllowedFullMoves(Chessboard chessboard) {
        HashSet<Move> allowedMoves = new HashSet<>();
        switch (type){

            case PAWN -> {
                if (clockwise) {
                    allowedMoves.addAll(MoveOneForward.concretise(chessboard, this, false, false, true));
                    if (this.getPosY() == PlayerService.getBaseY(player)) {
                        allowedMoves.addAll(MoveTwoForward.concretise(chessboard, this, false, false, true));
                    }
                    allowedMoves.addAll(MovePawnCaptureForward.concretise(chessboard, this, true, false, false));
                } else {
                    allowedMoves.addAll(MoveOneBackward.concretise(chessboard, this, false, false, true));
                    if (this.getPosY() == PlayerService.getBaseY(player) + 3) {
                        allowedMoves.addAll(MoveTwoBackward.concretise(chessboard, this, false, false, true));
                    }
                    allowedMoves.addAll(MovePawnCaptureBackward.concretise(chessboard, this, true, false, false));
                }
            }
            case ROOK -> {
                allowedMoves.addAll(MoveLeft.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveRight.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveForward.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveBackward.concretise(chessboard, this, true, false, true));
            }
            case KNIGHT -> {
                allowedMoves.addAll(MoveKnight.concretise(chessboard, this, true, true, true));
            }
            case BISHOP -> {
                allowedMoves.addAll(MoveDiagonal.concretise(chessboard, this, true, false, true));
            }
            case QUEEN -> {
                allowedMoves.addAll(MoveLeft.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveRight.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveForward.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveBackward.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveDiagonal.concretise(chessboard, this, true, false, true));
            }
            case KING -> {
                allowedMoves.addAll(MoveOneForward.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveOneBackward.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveOneLeft.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveOneRight.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveOneDiagonal.concretise(chessboard, this, true, false, true));
            }
            case CANNON -> {
                allowedMoves.addAll(Shoot.concretise(chessboard, this, true, false, false));
            }
            case FERZ -> {
                allowedMoves.addAll(MoveOneDiagonal.concretise(chessboard, this, true, false, true));
            }
            case WAZIR -> {
                allowedMoves.addAll(MoveOneForward.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveOneBackward.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveOneLeft.concretise(chessboard, this, true, false, true));
                allowedMoves.addAll(MoveOneRight.concretise(chessboard, this, true, false, true));
            }
        }
        return allowedMoves;
    }

    /**
     * Converts AllowedFullMoves to an array of Squares representing only the destination of the move.
     *
     * @param chessboard Chessboard
     * @return ArrayList of possible Squares to move to.
     */
    public ArrayList<Square> getAllowedMoves(Chessboard chessboard) {
        Set<Move> allowedFullMoves = getAllowedFullMoves(chessboard);
        ArrayList<Square> allowedMoves = new ArrayList<>();

        for (Move move :
                allowedFullMoves) {
            allowedMoves.add(move.getTo());
        }

        return allowedMoves;
    }

    public Color getColor() {
        return this.player.getColor();
    }

    public String getSymbol() {
        return this.getType().getSymbol();
    }

    public Position getPosition(){
        return new Position(posX, posY);
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
