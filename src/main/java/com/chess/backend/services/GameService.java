package com.chess.backend.services;

import com.chess.backend.models.Player;
import com.chess.backend.constants.COLOR;
import com.chess.backend.models.Game;
import com.chess.backend.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GameService {
    @Autowired
    private GameRepository repository;
    public String createGame(String[] playersNames){
        List<Player>  players = assignColorToPlayers(playersNames);
        Game game = new Game();
        game.setPlayers(players);
        Game savedGame = repository.save(game);
        return savedGame.getId();
    }

    private List<Player> assignColorToPlayers(String[] playersNames){
        List<COLOR> colorValues = new ArrayList<>(EnumSet.allOf(COLOR.class));
        List<Player> players = IntStream.range(0, playersNames.length).mapToObj(i -> {
            Player player = new Player();
            player.setColor(colorValues.get(i));
            player.setId(playersNames[i]);
            return player;
        }).collect(Collectors.toList());
        return players;
    }
}
