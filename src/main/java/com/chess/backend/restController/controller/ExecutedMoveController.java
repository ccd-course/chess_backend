package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.ExecutedMoveObject;
import com.chess.backend.restController.objects.ValidationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles the API-call regarding the executed move for a piece.
 *
 * @author Hannes Stuetzer
 */
@RestController
@RequestMapping("/executedMove")
public class ExecutedMoveController {
    /**
     * @param gameID the ID of the current game.
     * @param previousPiecePosition the previous position of the piece (before the move).
     * @param newPiecePosition the new position of the piece after the move.
     * @return Returns an {@link ExecutedMoveObject}.
     */
    @GetMapping
    public ExecutedMoveObject executedMove(@RequestParam(value = "gameID") int gameID,
                                           @RequestParam(value = "previousPiecePosition") int[] previousPiecePosition,
                                           @RequestParam(value = "newPiecePosition") int[] newPiecePosition){

        //TODO: make call to update the chessboard and validate the move

        //just testing
        //http://localhost:8080/executedMove?gameID=1&previousPiecePosition=1,2&newPiecePosition=2,2

        ValidationResponse validation = ValidationResponse.VALID_MOVE;
        Object[][] chessboard = new Object[3][2];
        chessboard[0][0] = "Pawn";

        return new ExecutedMoveObject(validation, chessboard);
    }
}
