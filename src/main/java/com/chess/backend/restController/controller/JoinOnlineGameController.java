package com.chess.backend.restController.controller;


import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.constants.Event;
import com.chess.backend.repository.GameRepository;
import com.chess.backend.restController.objects.JoinOnlineGameObject;
import com.chess.backend.services.ChessGameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the API-call to create a new game.
 *
 * @author Hannes Stuetzer
 */
@RestController
@CrossOrigin
@RequestMapping("/joinOnlineNewGame")
public class JoinOnlineGameController {

    private final ChessGameService gameService;
    private final GameRepository gameRepository;

    @Autowired
    public JoinOnlineGameController(ChessGameService newGameService, GameRepository gameRepository) {

        this.gameRepository = gameRepository;
        this.gameService = newGameService;
    }

    /**
     * Method that is called on a post request.
     *
     */
    @Operation(
            summary = "Start a new online game",
            description = "Returns the Game ID of a newly created game."
    )
    @PostMapping
    public int joinOnlineGame(@RequestBody JoinOnlineGameObject joinOnlineGameObject) {
        ChessGame game = this.gameService.getGame(joinOnlineGameObject.getGameId());
        List<Player> players = game.getPlayers();
        for(int i =0; i< players.size(); i++){
            Player player = players.get(i);
            if(player.getName()==null){
                player.setName(joinOnlineGameObject.getPlayer());
                players.set(i, player);
                break;
            }
        }
        game.setPlayers(players);
        game.setEvents(new ArrayList<>());
        List<Event> events = game.getEvents();

        events.add(Event.NEW_PLAYER_JOIN);
        game.setEvents(events);
        this.gameRepository.createNewGame(game.getId(), game);
        return game.getId();
    }
}