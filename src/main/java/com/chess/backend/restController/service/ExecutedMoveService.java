package com.chess.backend.restController.service;

import com.chess.backend.restController.controller.ExecutedMoveController;
import com.chess.backend.restController.objects.ExecutedMoveObject;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to say which move was executed.
 *
 * @author Hannes Stuetzer
 */
@Service
public class ExecutedMoveService {
    private ChessGameService gameService;

    @Autowired
    public ExecutedMoveService(ChessGameService gameService ){
        this.gameService = gameService;
    }

    /**
     * This method calls the {@link ChessGameService} to say which move was executed.
     *
     * @param executedMoveObject the object that is generated via the API-Call of the {@link ExecutedMoveController}.
     * @return containing value if the move is valid.
     */
    public String executedMove(ExecutedMoveObject executedMoveObject) {

        return gameService.executedMove(executedMoveObject.getGameID(), executedMoveObject.getPreviousPiecePosition(), executedMoveObject.getNewPiecePosition());
    }
}
