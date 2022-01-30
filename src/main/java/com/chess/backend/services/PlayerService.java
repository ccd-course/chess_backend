package com.chess.backend.services;

import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.constants.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Servie to initalize the players object and
 */
public class PlayerService {
    /**
     * initialize Players objects using their names
     * @param playerNames array of players names
     * @return Array of Player object
     */
    public static List<Player> initPlayers(String[] playerNames) {
        List<Player> players = new ArrayList<>();

//        Player[] players = new Player[playerNames.length];

        for (int i = 0; i < playerNames.length; i++) {
            Player player = new Player();

            player.setName(playerNames[i]);
            player.setId(i);
            player.setColor(Color.values()[i]);

            players.add(player);
        }

        return players;
    }

    /**
     * get first position of a Player in the Board
     * @param player Player Object
     * @return Player object in Y
     */
    public static int getBaseY(Player player) {
        return player.getColor().getPosition() * 9;
    }
}
