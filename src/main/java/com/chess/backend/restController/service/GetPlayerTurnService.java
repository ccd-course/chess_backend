package com.chess.backend.restController.service;

import com.chess.backend.restController.objects.GetPlayerTurnObject;
import com.chess.backend.services.GameService;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to get the player who is currently on turn.
 *
 * @author Hannes Stuetzer
 */
@Service
public class GetPlayerTurnService {
    /**
     * This methods gets the player who is currently on turn from the {@link GameService}.
     *
     * @param gameID the id of the current game.
     * @return containing the name of the player.
     */
    public GetPlayerTurnObject getPlayerTurn(int gameID) {
        GameService gc = GameService.getInstance();

        return new GetPlayerTurnObject(gameID, gc.getPlayerTurn(gameID));
    }
}
