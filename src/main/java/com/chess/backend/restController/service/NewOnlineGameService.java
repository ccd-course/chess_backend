package com.chess.backend.restController.service;

import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.constants.GameMode;
import com.chess.backend.repository.GameRepository;
import com.chess.backend.restController.objects.NewGameObject;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This class handles the call to create a new game.
 */
@Service
public class NewOnlineGameService  {
    private final ChessGameService gameService;
    private final GameRepository gameRepository;

    @Autowired
    public NewOnlineGameService(ChessGameService gameService, GameRepository gameRepository ){
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    public int getNewGameID(NewGameObject newOnlineGameObject) {
        String[] gamePlayers = new String[newOnlineGameObject.getNumberOfPlayers()];
        gamePlayers[0] = newOnlineGameObject.getPlayers()[0].getPlayerName();
//        List<String> sentPlayers = Arrays.stream(newOnlineGameObject.getPlayers()).map(player -> player.getPlayerName()).collect(Collectors.toList());
        ChessGame game = this.gameService.createNewGame(gamePlayers);
        game.setType(GameMode.ONLINE);
        int gameID = game.getId();
        this.gameRepository.createNewGame(gameID, game);
        return gameID;
    }
}
