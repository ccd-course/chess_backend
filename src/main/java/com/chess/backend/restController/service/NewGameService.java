package com.chess.backend.restController.service;

import com.chess.backend.GameController;
import org.springframework.stereotype.Service;

@Service
public class NewGameService {
    public int getNewGameID(String[] players){
        GameController gc = GameController.getInstance();

        if(gc.createNewGame(players)){
            return gc.getGameID();
        } else {
            return -1;
        }
    }
}
