package com.chess.backend.repository;

import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.gamemodel.ChessGame;

import java.util.HashMap;

/**
 * A GameRepository mocking class for testing.
 */
public class GameRepositoryMock implements IGameRepository {
    private final HashMap<Integer, ChessGame> games = new HashMap<>();

    @Override
    public ChessGame getGame(Integer gameId) {
        return games.get(gameId);
    }

    @Override
    public void createNewGame(Integer gameId, ChessGame game) {
        games.put(gameId, game);
    }
}
