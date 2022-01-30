package com.chess.backend.restController.objects;

import com.chess.backend.domain.controllers.objects.INewPlayerObject;
import com.chess.backend.domain.controllers.objects.INewPlayersObject;
import com.chess.backend.restController.controller.NewGameController;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

/**
 * This class is created via the API-Call of {@link NewGameController}.
 *
 * @author Hannes Stuetzer
 */
@Data
public class NewPlayersObject implements INewPlayersObject {
    /**
     * holds some simple player objects only containing the name of the players.
     */
    private NewPlayerObject[] players;

    public NewPlayersObject() {
    }

    /**
     * Iterates through all the players and gets the name of them.
     *
     * @return an array of all the player names.
     */
    @Override
    @Hidden
    public String[] getAllPlayerNames() {
        String[] allPlayerNames = new String[players.length];

        for (int i = 0; i < players.length; i++) {
            allPlayerNames[i] = players[i].getPlayerName();
        }

        return allPlayerNames;
    }
}
