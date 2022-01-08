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
public class Knight implements IPiece {
    private Square square;
    private Player player;
    private Chessboard chessboard; // <-- this relations isn't in class diagram, but it's necessary :/
    private PieceType type = PieceType.KNIGHT;
    private boolean motioned;
    private final boolean clockwise; // TODO: 4 of the 8 Pawns move in the other direction. Initialize accordingly.


    public Knight(Player player, boolean clockwise) {
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
        allowedMoves.addAll(MoveKnight.concretise(game, this.square, true, true, true));
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

    /**
     * Method is useful for out of bounds protection
     *
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if parameters are out of bounds (array)
     */
    @Override
    public boolean isout(int x, int y) {
        return x < 0 || x > 7 || y < 0 || y > 7;
    }

    /**
     * @param x y position on chessboard
     * @param y y position on chessboard
     * @return true if can move, false otherwise
     */
    @Override
    public boolean checkPiece(int x, int y) {
        if (chessboard.getSquares()[x][y].getPiece() != null
                && chessboard.getSquares()[x][y].getPiece().getType().equals(PieceType.KING)) {
            return false;
        }
        IPiece piece = chessboard.getSquares()[x][y].getPiece();
        //or piece is another player
        return piece == null || //if this sqhuare is empty
                piece.getPlayer() != this.player;
    }

    /**
     * Method check if piece has other owner than calling piece
     *
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if owner(player) is different
     */
    @Override
    public boolean otherOwner(int x, int y) {
        Square sq = chessboard.getSquares()[x][y];
        if (sq.getPiece() == null) {
            return false;
        }
        return this.player != sq.getPiece().getPlayer();
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
                ", chessboard=" + chessboard +
                ", player=" + player +
                ", motioned=" + motioned +
                ", clockwise=" + clockwise +
                '}';
    }
}
