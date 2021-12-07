package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.GetPlayerTurnObject;
import com.chess.backend.restController.service.GetPlayerTurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles the API-call to get the player who is currently on the turn.
 *
 * @author Hannes Stuetzer
 */
@RestController
@RequestMapping("/getPlayerTurn")
public class GetPlayerTurnController {
    private final GetPlayerTurnService playerTurnService;

    @Autowired
    public GetPlayerTurnController(GetPlayerTurnService playerTurnService){
        this.playerTurnService = playerTurnService;
    }

    /**
     * Method that is called on a get request.
     *
     * @param gameID the id of the current game (as params).
     * @return a {@link GetPlayerTurnObject} containing the id of the game and the name of the player.
     */
    @GetMapping
    public GetPlayerTurnObject getPlayerTurn(@RequestParam(value = "gameID") int gameID){
        return playerTurnService.getPlayerTurn(gameID);
    }
}
