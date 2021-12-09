package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.GetPlayerTurnController;

/**
 * This class is send as a response from the {@link GetPlayerTurnController}.
 *
 * @author Hannes Stuetzer
 */
public class GetPlayerTurnObject {
    /**
     * the id of the current game.
     */
    private int gameID;
    /**
     * the name of the player who is currently on turn.
     */
    private String player;

    public GetPlayerTurnObject(int gameID, String player) {
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
