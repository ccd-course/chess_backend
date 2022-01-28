package com.chess.backend.domain.repository;

import com.chess.backend.gamemodel.ChessGame;

import java.util.HashMap;
import java.util.HashSet;

public class GameRepositoryMock implements IGameRepository {
    private HashMap<Integer, ChessGame> games = new HashMap<>();

    @Override
    public ChessGame getGame(Integer gameId) {
        return games.get(gameId);
    }

    @Override
    public void createNewGame(Integer gameId, ChessGame game) {
        games.put(gameId, game);
    }
}
