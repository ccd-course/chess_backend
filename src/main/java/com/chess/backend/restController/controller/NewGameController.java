package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.NewPlayersObject;
import com.chess.backend.restController.service.NewGameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * Method that is called on a post request.
     *
     * @param players in the request body containing the playerNames.
     * @return the id of the new created game.
     */
    @Operation(
            summary = "Start a new game",
            description = "Returns the Game ID of a newly created game."
    )
    @PostMapping
    public int createNewGame(@RequestBody NewPlayersObject players) {

        return newGameService.getNewGameID(players);
    }
}
