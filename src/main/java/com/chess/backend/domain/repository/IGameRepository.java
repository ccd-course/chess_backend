package com.chess.backend.domain.repository;


import com.chess.backend.gamemodel.ChessGame;

public interface  IGameRepository{
    ChessGame getGame(Integer gameId);
    void createNewGame(Integer gameId, ChessGame game);
}
