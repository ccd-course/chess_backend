package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.MoveRequestObject;
import com.chess.backend.restController.service.MoveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles the API-call to get all possible moves for a piece.
 *
 * @author Hannes Stuetzer
 */
@RestController
@RequestMapping("/moveRequest")
public class MoveRequestController {

    private  final MoveRequestService moveRequestService;

    @Autowired
    public MoveRequestController(MoveRequestService moveRequestService){
        this.moveRequestService = moveRequestService;
    }

    /**
     * for testing API: http://localhost:8080/moveRequest?gameID=1234&pieceID=Pawn&piecePosition=1,2
     *
     * @param gameID
     * @param pieceID
     * @param piecePosition
     * @return
     */
    @GetMapping
    public MoveRequestObject getPossibleMoves(@RequestParam(value = "gameID") int gameID,
                                              @RequestParam(value = "pieceID") String pieceID,
                                              @RequestParam(value = "piecePosition") int[] piecePosition){

        return moveRequestService.getPossibleMoves(gameID, pieceID, piecePosition);
    }
}