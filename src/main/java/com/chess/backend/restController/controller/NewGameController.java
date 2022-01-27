package com.chess.backend.restController.controller;

import com.chess.backend.domain.services.INewGameService;
import com.chess.backend.restController.objects.NewPlayersObject;
import com.chess.backend.restController.service.NewChessGameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles the API-call to create a new game.
 *
 * @author Hannes Stuetzer
 */
@RestController
@CrossOrigin
@RequestMapping("/createNewGame")
public class NewGameController {

    private final INewGameService newGameService;

    @Autowired
    public NewGameController(@Qualifier("NewChessGameService")INewGameService newGameService) {
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
