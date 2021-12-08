package com.chess.backend.restController.service;

import com.chess.backend.restController.controller.MoveRequestController;
import com.chess.backend.restController.objects.MoveRequestInputObject;
import com.chess.backend.restController.objects.MoveRequestOutputObject;
import com.chess.backend.services.GameService;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to get all possible moves for a chess piece.
 *
 * @author Hannes Stuetzer
 */
@Service
public class MoveRequestService {
    /**
     * This method gets the possible moves from the {@link GameService}.
     *
     * @param moveRequestInputObject the object that is generated via the API-Call of the {@link MoveRequestController}.
     * @return containing all possible moves.
     */
    public MoveRequestOutputObject getPossibleMoves(MoveRequestInputObject moveRequestInputObject) {

        GameService gc = GameService.getInstance();

        return new MoveRequestOutputObject(gc.getPossibleMoves(moveRequestInputObject.getGameID(), moveRequestInputObject.getPiecePosition()));
    }
}
