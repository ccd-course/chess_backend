package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.ExecutedMoveObject;
import com.chess.backend.restController.objects.NewGameObject;
import com.chess.backend.restController.objects.ValidationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/executedMove")
public class ExecutedMoveController {
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
