package com.chess.backend.domain.models;

import com.chess.backend.gamemodel.Moves;
import com.chess.backend.gamemodel.Square;

import java.util.ArrayList;

/**
 * Interface of Board class
 */
public interface IBoard {
    int getNumberOfPlayers();

    ArrayList<ArrayList<Square>>  getSquares();

    boolean isBreakCastling();

    Moves getMoves_history();

    void setNumberOfPlayers(int numberOfPlayers);

    void setSquares(ArrayList<ArrayList<Square>>  squares);


    void setBreakCastling(boolean breakCastling);

    void setMoves_history(Moves moves_history);

    boolean equals(Object o);

    int hashCode();

    String toString();
}
