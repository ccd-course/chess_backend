package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.MoveRequestInputObject;
import com.chess.backend.restController.objects.MoveRequestOutputObject;
import com.chess.backend.restController.service.MoveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public MoveRequestOutputObject getPossibleMoves(@RequestBody MoveRequestInputObject moveRequestInputObject){

        return moveRequestService.getPossibleMoves(moveRequestInputObject);
    }
}