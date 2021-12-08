package com.chess.backend.services;

public class GameIDService {
    public int getNewGameID() {
        //TODO: would perform a request to the database to get a new not used gameID

        return 1 + (int) (Math.random() * ((1000 - 1) + 1));
    }
}
