package com.chess.backend.restController.objects;

import com.chess.backend.gamemodel.constants.GameMode;
import lombok.Data;

@Data
public class NewGameObject {
    private GameMode type;
    private Integer numberOfPlayers;
    private NewPlayerObject[] players;
}
