package com.chess.backend.restController.service;

import com.chess.backend.restController.objects.GetPlayerTurnObject;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to get the player who is currently on turn.
 *
 * @author Hannes Stuetzer
 */
@Service
public class GetPlayerTurnService {
    private ChessGameService gameService;

    @Autowired
    public GetPlayerTurnService(ChessGameService gameService ){
        this.gameService = gameService;
    }

    /**
     * This methods gets the player who is currently on turn from the {@link ChessGameService}.
     *
     * @param gameID the id of the current game.
     * @return containing the name of the player.
     */
    public GetPlayerTurnObject getPlayerTurn(int gameID) {

        return new GetPlayerTurnObject(gameID, this.gameService.getPlayerTurn(gameID));
    }
}
