package com.chess.backend.restController.service;

import com.chess.backend.restController.controller.ExecutedMoveController;
import com.chess.backend.services.GameService;
import com.chess.backend.restController.objects.ExecutedMoveObject;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to say which move was executed.
 *
 * @author Hannes Stuetzer
 */
@Service
public class ExecutedMoveService {
    /**
     * This method calls the {@link GameService} to say which move was executed.
     *
     * @param executedMoveObject the object that is generated via the API-Call of the {@link ExecutedMoveController}.
     * @return containing value if the move is valid.
     */
    public boolean executedMove(ExecutedMoveObject executedMoveObject){
        GameService gc = GameService.getInstance();

        return gc.executedMove(executedMoveObject.getGameID(), executedMoveObject.getPreviousPiecePosition(), executedMoveObject.getNewPiecePosition());
    }
}
