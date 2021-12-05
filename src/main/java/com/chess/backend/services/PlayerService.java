package com.chess.backend.services;

import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.constants.Color;

public class PlayerService {
    public Player[] initPlayers(String[] playerNames){
        Player[] players = new Player[playerNames.length];

        for(int i = 0; i<playerNames.length; i++){
            Player player = new Player();

            player.setName(playerNames[i]);
            player.setId(i);
            player.setColor(Color.values()[i]);

            players[i] = player;
        }

        return players;
    }
}
