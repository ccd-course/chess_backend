package com.chess.backend.restController.objects;

import java.util.List;

public class NewPlayersObject {
    private NewPlayerObject[] players;

    public NewPlayersObject(){}

    public NewPlayerObject[] getPlayers() {
        return players;
    }

    public void setPlayers(NewPlayerObject[] players) {
        this.players = players;
    }

    public String[] getAllPlayerNames(){
        String[] allPlayerNames = new String[players.length];

        for(int i = 0; i<players.length; i++){
            allPlayerNames[i] = players[i].getPlayerName();
        }

        return allPlayerNames;
    }
}
