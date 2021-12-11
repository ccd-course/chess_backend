package com.chess.backend.restController.objects;

import lombok.Data;

/**
 * This class is a simple player object for handling an API-Call.
 *
 * @author Hannes Stuetzer
 */
@Data
public class NewPlayerObject {
    /**
     * the name of a player.
     */
    private String playerName;

    public NewPlayerObject() {
    }
}
