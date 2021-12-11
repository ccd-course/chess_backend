package com.chess.backend.restController.objects;

import com.chess.backend.domain.controllers.objects.INewPlayerObject;
import lombok.Data;

/**
 * This class is a simple player object for handling an API-Call.
 *
 * @author Hannes Stuetzer
 */
@Data
public class NewPlayerObject implements INewPlayerObject {
    /**
     * the name of a player.
     */
    private String playerName;

    public NewPlayerObject() {
    }
}
