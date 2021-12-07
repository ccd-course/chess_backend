package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.NewGameController;

/**
 * This class is created via the API-Call of {@link NewGameController}.
 *
 * @author Hannes Stuetzer
 */
public class NewPlayersObject {
    /**
     * holds some simple player objects only containing the name of the players.
     */
    private NewPlayerObject[] players;

    public NewPlayersObject(){}

    public NewPlayerObject[] getPlayers() {
        return players;
    }

    public void setPlayers(NewPlayerObject[] players) {
        this.players = players;
    }

    /**
     * Iterates through all the players and gets the name of them.
     *
     * @return an array of all the player names.
     */
    public String[] getAllPlayerNames(){
        String[] allPlayerNames = new String[players.length];

        for(int i = 0; i<players.length; i++){
            allPlayerNames[i] = players[i].getPlayerName();
        }

        return allPlayerNames;
    }
}
