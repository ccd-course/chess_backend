package com.chess.backend.restController.service;

import com.chess.backend.services.GameService;
import com.chess.backend.restController.objects.GetPlayerTurnObject;

public class GetPlayerTurnService {
    public GetPlayerTurnObject getPlayerTurn(int gameID){
        GameService gc = GameService.getInstance();

        return new GetPlayerTurnObject(gameID, gc.getPlayerTurn(gameID));
    }
}
