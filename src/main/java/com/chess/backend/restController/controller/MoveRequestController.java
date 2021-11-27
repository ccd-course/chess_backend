package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.MoveRequestObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moveRequest")
public class MoveRequestController {

    @GetMapping
    public MoveRequestObject sendPossibleMoves(@RequestParam(value = "gameID") int gameID,
                                               @RequestParam(value = "piecePosition") int[] piecePosition){

        //TODO: call method to get the possible moves for the piece

        //just testing
        //http://localhost:8080/moveRequest?gameID=1&piecePosition=1,2
        int pieceID = 32;
        int[] possibleMoves = new int[4];
        possibleMoves[0] = 2;
        possibleMoves[1] = 3;
        possibleMoves[2] = 2;
        possibleMoves[3] = 4;

        return new MoveRequestObject(pieceID, piecePosition, possibleMoves);
    }
}
