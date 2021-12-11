package com.chess.backend.domain.models;

import com.chess.backend.gamemodel.Moves;
import com.chess.backend.gamemodel.Player;

public interface IGame {
    IBoard getChessboard();

    Moves getMoves();

    Player getActivePlayer();

    int getId();

    Player[] getPlayers();

    void setChessboard(com.chess.backend.domain.models.IBoard chessboard);

    void setMoves(Moves moves);

    void setActivePlayer(Player activePlayer);

    void setId(int id);

    void setPlayers(Player[] players);

    boolean equals(Object o);

    boolean canEqual(Object other);

    int hashCode();

    String toString();
}
