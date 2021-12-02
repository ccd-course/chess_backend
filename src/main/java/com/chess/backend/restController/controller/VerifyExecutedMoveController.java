package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.VerifyExecutedMoveObject;
import com.chess.backend.restController.service.VerifyExecutedMoveService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/verifyExecutedMove")
public class VerifyExecutedMoveController {
    private final VerifyExecutedMoveService verifyExecutedMoveService;

    @Autowired
    public VerifyExecutedMoveController(VerifyExecutedMoveService verifyExecutedMoveService){
        this.verifyExecutedMoveService = verifyExecutedMoveService;
    }

    /**
     * @param gameID the ID of the current game.
     * @param previousPiecePosition the previous position of the piece (before the move).
     * @param newPiecePosition the new position of the piece after the move.
     * @return Returns an {@link VerifyExecutedMoveObject}.
     */
    @GetMapping
    public VerifyExecutedMoveObject executedMove(@RequestParam(value = "gameID") int gameID,
                                                 @RequestParam(value = "pieceID") String pieceID,
                                                 @RequestParam(value = "previousPiecePosition") int[] previousPiecePosition,
                                                 @RequestParam(value = "newPiecePosition") int[] newPiecePosition){

        return verifyExecutedMoveService.verifyExecutedMove(gameID, pieceID, previousPiecePosition, newPiecePosition);
    }
}
