package com.chess.backend.domain.models;

import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Moves;
import com.chess.backend.gamemodel.Player;

public interface IGame {
    Chessboard getChessboard();

    Moves getMoves();

    Player getActivePlayer();

    int getId();

    Player[] getPlayers();

    void setChessboard(Chessboard chessboard);

    void setMoves(Moves moves);

    void setActivePlayer(Player activePlayer);

    void setId(int id);

    void setPlayers(Player[] players);
}
