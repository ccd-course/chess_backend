package com.chess.backend.domain.models;

import com.chess.backend.gamemodel.Moves;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.Square;

public interface IBoard {
    int getNumberOfPlayers();

    Square[][] getSquares();

    Piece getTwoSquareMovedPawn();

    Piece getTwoSquareMovedPawn2();

    boolean isBreakCastling();

    Moves getMoves_history();

    void setNumberOfPlayers(int numberOfPlayers);

    void setSquares(Square[][] squares);

    void setTwoSquareMovedPawn(Piece twoSquareMovedPawn);

    void setTwoSquareMovedPawn2(Piece twoSquareMovedPawn2);

    void setBreakCastling(boolean breakCastling);

    void setMoves_history(Moves moves_history);

    boolean equals(Object o);

    boolean canEqual(Object other);

    int hashCode();

    String toString();
}
