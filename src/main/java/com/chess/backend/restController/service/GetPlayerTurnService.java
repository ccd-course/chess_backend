package com.chess.backend.restController.service;

import com.chess.backend.service.GameService;
import com.chess.backend.restController.objects.GetPlayerTurnObject;
import org.springframework.stereotype.Service;

@Service
public class GetPlayerTurnService {
    public GetPlayerTurnObject getPlayerTurn(int gameID){
        GameService gc = GameService.getInstance();

        return new GetPlayerTurnObject(gameID, gc.getPlayerTurn(gameID));
    }
}
