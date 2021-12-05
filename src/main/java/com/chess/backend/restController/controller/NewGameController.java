package com.chess.backend.restController.controller;

import com.chess.backend.restController.service.NewGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles the API-call to create a new game.
 *
 * @author Hannes Stuetzer
 */
@RestController
@RequestMapping("/createNewGame")
public class NewGameController {

    private final NewGameService newGameService;

    @Autowired
    public NewGameController(NewGameService newGameService) {
        this.newGameService = newGameService;
    }

    /**
     * for testing the API: http://localhost:8080/createNewGame?players=Hannes,Valentin,Amro
     *
     * @param players array of the player names
     * @return the game ID of the new created game
     */
    @GetMapping
    public int getNewGameID(@RequestParam(value = "players") String[] players){
       return newGameService.getNewGameID(players);
    }
}
