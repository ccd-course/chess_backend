package com.chess.backend.restController.service;

import com.chess.backend.domain.controllers.objects.INewPlayersObject;
import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.domain.services.INewGameService;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.restController.controller.NewGameController;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to create a new game.
 */
@Service
@Qualifier("NewChessGameService")
public class NewChessGameService implements INewGameService {
    private final ChessGameService gameService;
    private final IGameRepository gameRepository;

    @Autowired
    public NewChessGameService(ChessGameService gameService, IGameRepository gameRepository ){
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }
    /**
     * This method tells the {@link ChessGameService} to create a new game and gets the id of the new game.
     *
     * @param players the object that is generated via the API-Call of the {@link NewGameController}.
     * @return the id of the new created game.
     */
    @Override
    public int getNewGameID(INewPlayersObject players) {
        ChessGame game = this.gameService.createNewGame(players.getAllPlayerNames());
        int gameID = game.getId();
        this.gameRepository.createNewGame(gameID, game);
        return game.getId();
    }
}
