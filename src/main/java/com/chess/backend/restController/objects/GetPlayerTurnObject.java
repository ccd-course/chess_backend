package com.chess.backend.restController.objects;

public class GetPlayerTurnObject {
    private int gameID;
    private String player;

    public GetPlayerTurnObject(int gameID, String player){
        this.gameID = gameID;
        this.player = player;
    }

    public int getGameID() {
        return gameID;
    }

    public String getPlayer() {
        return player;
    }
}
