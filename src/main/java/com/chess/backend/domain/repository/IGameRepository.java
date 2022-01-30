package com.chess.backend.domain.repository;


import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.repository.metadata.EventObject;

import java.util.List;

/**
 * Interface of GameRepository class
 */
public interface  IGameRepository{
    ChessGame getGame(Integer gameId);
    void createNewGame(Integer gameId, ChessGame game, List<EventObject> events);
    void createNewGame(Integer gameId, ChessGame game);
    void updateGame(Integer gameId, ChessGame game, List<EventObject> events);
    void updateGame(Integer gameId, ChessGame game);

}
