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
    private final GameRepository gameRepository;
    @Autowired
    public NewOnlineGameService(GameRepository gameRepository ){
        this.gameRepository = gameRepository;
    }
    public int getNewGameID(NewOnlineGameObject newOnlineGameObject) {
        String[] players = new String[newOnlineGameObject.getNumberOfPlayers()];
        players[0] = newOnlineGameObject.getPlayer();
        ChessGameService gc = ChessGameService.getInstance();
        ChessGame game = gc.createNewOnlineGame(players);
        int gameID = game.getId();
        this.gameRepository.createNewGame(gameID, game);
        return gameID;
//        if (gc.createNewGame(players.getAllPlayerNames())) {
//            return gc.getGameID();
//        } else {
//            return -1;
//        }
    }
}
