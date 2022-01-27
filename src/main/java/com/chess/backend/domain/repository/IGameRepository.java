package com.chess.backend.domain.repository;


import com.chess.backend.gamemodel.ChessGame;

public interface  IGameRepository{
    public ChessGame getGame(Integer gameId);
    public void createNewGame(Integer gameId, ChessGame game);
}
