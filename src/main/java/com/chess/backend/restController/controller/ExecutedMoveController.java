package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.ExecutedMoveObject;
import com.chess.backend.restController.service.ExecutedMoveService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles the API-call regarding the executed move for a piece.
 *
 * @author Hannes Stuetzer
 */
@RestController
@CrossOrigin
@RequestMapping("/executedMove")
public class ExecutedMoveController {
    private final ExecutedMoveService executedMoveService;

    @Autowired
    public ExecutedMoveController(ExecutedMoveService executedMoveService) {
        this.executedMoveService = executedMoveService;
    }

    /**
     * Method that is called on a post request.
     *
     * @param executedMoveObject in the request body (json object).
     * @return true (and HttpStatus.OK (200)) if move was successful and false (and HttpStatus.BAD_REQUEST (400)) if the move was not successful.
     */
    @Operation(
            summary = "Execute a move in the backend",
            description = "Execute a move in the backend. " +
                    "The move consists of the previous position and a the new piece position."
    )
    @PostMapping
    public String executedMove(@RequestBody ExecutedMoveObject executedMoveObject) {

        return executedMoveService.executedMove(executedMoveObject);
    }
}
