package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.ExecutedMoveObject;
import com.chess.backend.restController.service.ExecutedMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles the API-call regarding the executed move for a piece.
 *
 * @author Hannes Stuetzer
 */
@RestController
@RequestMapping("/executedMove")
public class ExecutedMoveController {
    private final ExecutedMoveService executedMoveService;

    @Autowired
    public ExecutedMoveController(ExecutedMoveService executedMoveService){
        this.executedMoveService = executedMoveService;
    }

    /**
     * Method that is called on a post request.
     *
     * @param executedMoveObject in the request body (json object).
     * @return true (and HttpStatus.OK (200)) if move was successful and false (and HttpStatus.BAD_REQUEST (400)) if the move was not successful.
     */
    @PostMapping
    public ResponseEntity<Boolean> executedMove(@RequestBody ExecutedMoveObject executedMoveObject){
        if(executedMoveService.executedMove(executedMoveObject)){
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
