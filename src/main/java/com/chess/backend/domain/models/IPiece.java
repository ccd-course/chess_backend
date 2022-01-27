package com.chess.backend.domain.models;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;

import java.util.ArrayList;
import java.util.HashSet;

public interface IPiece {
    /**
     * Returns all allowed moves of the piece. The moves for each pieceType are composed of several abstract moves.
     *
     * @param chessboard The chessboard.
     * @return A HashSet of all allowed moves of the piece in this individual game context.
     */
    HashSet<Move> getAllowedFullMoves(Chessboard chessboard);

    /**
     * Converts AllowedFullMoves to an array of Squares representing only the destination of the move.
     *
     * @param chessboard The chessboard.
     * @return ArrayList of possible Squares to move to.
     */
    ArrayList<Square> getAllowedMoves(Chessboard chessboard);

    boolean getMainDirection();

    PieceType getType();

    void setType(PieceType type);

    Color getColor();

    String getSymbol();

    boolean isMotioned();

    void setMotioned(boolean motioned);

    Player getPlayer();

    boolean isClockwise();

    @Override
    String toString();

    Square getSquare();

    Chessboard getChessboard();

    void setSquare(Square square);

    void setPlayer(Player player);

    void setChessboard(Chessboard chessboard);

    boolean equals(Object o);

    int hashCode();
}
