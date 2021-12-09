package com.chess.backend.restController.objects;

/**
 * This class is a simple player object for handling an API-Call.
 *
 * @author Hannes Stuetzer
 */
public class NewPlayerObject {
    /**
     * the name of a player.
     */
    private String playerName;

    public NewPlayerObject() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
