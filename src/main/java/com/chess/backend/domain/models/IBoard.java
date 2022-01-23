package com.chess.backend.domain.models;

import com.chess.backend.gamemodel.Moves;
import com.chess.backend.gamemodel.Square;

import java.util.ArrayList;

public interface IBoard {
    int getNumberOfPlayers();

    ArrayList<ArrayList<Square>>  getSquares();

    IPiece getTwoSquareMovedPawn();

    IPiece getTwoSquareMovedPawn2();

    boolean isBreakCastling();

    Moves getMoves_history();

    void setNumberOfPlayers(int numberOfPlayers);

    void setSquares(ArrayList<ArrayList<Square>>  squares);

    void setTwoSquareMovedPawn(IPiece twoSquareMovedPawn);

    void setTwoSquareMovedPawn2(IPiece twoSquareMovedPawn2);

    void setBreakCastling(boolean breakCastling);

    void setMoves_history(Moves moves_history);

    boolean equals(Object o);

    int hashCode();

    String toString();
}
