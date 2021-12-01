package com.chess.backend.restController.service;

import com.chess.backend.GameController;
import com.chess.backend.restController.objects.GetPlayerTurnObject;

public class GetPlayerTurnService {
    public GetPlayerTurnObject getPlayerTurn(int gameID){
        GameController gc = GameController.getInstance();

        return new GetPlayerTurnObject(gameID, gc.getPlayerTurn(gameID));
    }
}
