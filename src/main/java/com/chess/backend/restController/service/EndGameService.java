package com.chess.backend.restController.service;

import com.chess.backend.services.ChessGameService;
import org.springframework.stereotype.Service;

/**
 *  This class handles the call to end a game.
 */
@Service
public class EndGameService {
    /**
     * This method tells the {@link ChessGameService} to end the game with the responding game ID and returns a boolean value indicating the success.
     *
     * @param gameID the ID of the game that should be ended.
     * @return a boolean value indicating if the ending of the game was successful or not.
     */
    public boolean endGame(int gameID) {
        ChessGameService gc = ChessGameService.getInstance();

        return gc.endGame(gameID);
    }
}
