package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.NewPlayersObject;
import com.chess.backend.restController.service.EndGameService;
import com.chess.backend.restController.service.NewGameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles the API-call to end a game.
 *
 * @author Hannes Stuetzer
 */
@RestController
@CrossOrigin
@RequestMapping("/endGame")
public class EndGameController {

    private final EndGameService endGameService;

    @Autowired
    public EndGameController(EndGameService endGameService) {
        this.endGameService = endGameService;
    }

    /**
     * Method that is called on a post request.
     *
     * @param gameID the ID of the game that should be ended.
     * @return true if the ending of the game was successful and false if it was not successful (a reasons for example could be a wrong game ID).
     */
    @Operation(
            summary = "End a game",
            description = "Returns the Game ID of a newly created game."
    )
    @PostMapping
    public ResponseEntity<Boolean> endGame(@RequestParam(value = "gameID") int gameID) {
        if(endGameService.endGame(gameID)){
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
