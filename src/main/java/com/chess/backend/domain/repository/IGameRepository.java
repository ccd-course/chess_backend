package com.chess.backend.domain.repository;


import com.chess.backend.gamemodel.ChessGame;

/**
 * Interface of GameRepository class
 */
public interface  IGameRepository{
    ChessGame getGame(Integer gameId);
    void createNewGame(Integer gameId, ChessGame game);
}
