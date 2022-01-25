package com.chess.backend.restController.service;

import com.chess.backend.domain.controllers.objects.INewPlayersObject;
import com.chess.backend.domain.services.INewGameService;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.repository.Firebase;
import com.chess.backend.repository.GameRepository;
import com.chess.backend.restController.controller.NewGameController;
import com.chess.backend.restController.objects.NewOnlineGameObject;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * This class handles the call to create a new game.
 */
@Service
public class NewOnlineGameService  {
    private ChessGameService gameService;
    private GameRepository gameRepository;

    @Autowired
    public NewOnlineGameService(ChessGameService gameService, GameRepository gameRepository ){
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    public int getNewGameID(NewOnlineGameObject newOnlineGameObject) {
        String[] players = new String[newOnlineGameObject.getNumberOfPlayers()];
        players[0] = newOnlineGameObject.getPlayer();
        ChessGame game = this.gameService.createNewOnlineGame(players);
        int gameID = game.getId();
        this.gameRepository.createNewGame(gameID, game);
        return gameID;
    }
}
