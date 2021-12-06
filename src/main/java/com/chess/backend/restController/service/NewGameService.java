package com.chess.backend.restController.service;

import com.chess.backend.restController.objects.NewPlayerObject;
import com.chess.backend.restController.objects.NewPlayersObject;
import com.chess.backend.services.GameService;
import org.springframework.stereotype.Service;

@Service
public class NewGameService {
    public int getNewGameID(NewPlayersObject players){
        GameService gc = GameService.getInstance();

        if(gc.createNewGame(players.getAllPlayerNames())){
            return gc.getGameID();
        } else {
            return -1;
        }
    }
}
