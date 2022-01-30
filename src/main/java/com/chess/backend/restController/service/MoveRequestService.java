package com.chess.backend.restController.service;

import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.restController.controller.MoveRequestController;
import com.chess.backend.restController.objects.MoveRequestInputObject;
import com.chess.backend.restController.objects.MoveRequestOutputObject;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to get all possible moves for a chess piece.
 *
 * @author Hannes Stuetzer
 */
@Service
public class MoveRequestService {
    private final ChessGameService gameService;

    @Autowired
    public MoveRequestService(ChessGameService gameService ){
        this.gameService = gameService;
    }

    /**
     * This method gets the possible moves from the {@link ChessGameService}.
     *
     * @param moveRequestInputObject the object that is generated via the API-Call of the {@link MoveRequestController}.
     * @return containing all possible moves.
     */
    public MoveRequestOutputObject getPossibleMoves(MoveRequestInputObject moveRequestInputObject) {

        ChessGame game = this.gameService.getGame(moveRequestInputObject.getGameID());
        return new MoveRequestOutputObject(gameService.getPossibleMoves(game, moveRequestInputObject.getPiecePosition()));
    }
}
