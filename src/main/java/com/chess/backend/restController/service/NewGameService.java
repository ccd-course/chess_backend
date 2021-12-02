package com.chess.backend.restController.service;

import com.chess.backend.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class NewGameService {
    public int getNewGameID(String[] players){
        GameService gc = GameService.getInstance();

        if(gc.createNewGame(players)){
            return gc.getGameID();
        } else {
            return -1;
        }
    }
}
