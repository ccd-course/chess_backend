package com.chess.backend;

import com.chess.backend.gamemodel.Game;

public class GameController {
    private static final GameController gameController = new GameController();
    private Game game;

    private GameController() {
    }

    public static GameController getInstance() {
        return gameController;
    }

    public boolean createNewGame(int playerNumber){
        game = new Game();
        return true;
    }
}
