package com.chess.backend.restController.service;

import com.chess.backend.restController.controller.NewGameController;
import com.chess.backend.restController.objects.NewPlayersObject;
import com.chess.backend.services.GameService;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to create a new game.
 */
@Service
public class NewGameService {
    /**
     * This method tells the {@link GameService} to create a new game and gets the id of the new game.
     *
     * @param players the object that is generated via the API-Call of the {@link NewGameController}.
     * @return the id of the new created game.
     */
    public int getNewGameID(NewPlayersObject players) {
        GameService gc = GameService.getInstance();

        if (gc.createNewGame(players.getAllPlayerNames())) {
            return gc.getGameID();
        } else {
            return -1;
        }
    }
}
