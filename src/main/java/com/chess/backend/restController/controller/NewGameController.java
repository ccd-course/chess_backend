package com.chess.backend.restController.controller;

import com.chess.backend.domain.services.INewGameService;
import com.chess.backend.gamemodel.constants.GameMode;
import com.chess.backend.restController.objects.NewGameObject;
import com.chess.backend.restController.service.NewOnlineGameService;

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

    private final NewOnlineGameService newOnlineGameService;
    private final INewGameService newGameService;

    @Autowired
    public NewGameController(NewOnlineGameService newOnlineGameService,
                                        @Qualifier("NewChessGameService") INewGameService newGameService) {
        this.newOnlineGameService = newOnlineGameService;
        this.newGameService = newGameService;
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
    public int createNewGame(@RequestBody NewGameObject newGameObject) {
        if(newGameObject.getType()== GameMode.ONLINE ){
            System.out.println("ONLINE WORKS");
            return newOnlineGameService.getNewGameID(newGameObject);

        }
        else if(newGameObject.getType()== GameMode.OFFLINE){
            return newGameService.getNewGameID(newGameObject);

        }{
            throw new Error("Type is not accepted");
        }
    }
}
