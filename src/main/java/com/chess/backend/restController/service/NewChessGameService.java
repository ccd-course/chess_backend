package com.chess.backend.restController.service;

import com.chess.backend.domain.controllers.objects.INewPlayersObject;
import com.chess.backend.domain.services.INewGameService;
import com.chess.backend.restController.controller.NewGameController;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to create a new game.
 */
@Service
@Qualifier("NewChessGameService")
public class NewChessGameService implements INewGameService {
    /**
     * This method tells the {@link ChessGameService} to create a new game and gets the id of the new game.
     *
     * @param players the object that is generated via the API-Call of the {@link NewGameController}.
     * @return the id of the new created game.
     */
    @Override
    public int getNewGameID(INewPlayersObject players) {
        ChessGameService gc = ChessGameService.getInstance();

        if (gc.createNewGame(players.getAllPlayerNames())) {
            return gc.getGameID();
        } else {
            return -1;
        }
    }
}