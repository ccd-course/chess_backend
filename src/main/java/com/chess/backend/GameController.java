package com.chess.backend;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Square;

public class GameController {
    private static final GameController gameController = new GameController();
    private Game game;

    private GameController() {
    }

    public static GameController getInstance() {
        return gameController;
    }

    public boolean createNewGame(String[] players){
        game = new Game();
        //TODO: a new game has to be initialized

        return true;
    }

    public int getGameID(){
        return game.getId();
    }

    public Square[][] getChessboard(int gameID){
        //TODO: handle the getting of the chessboard with the gameID
        //TODO: implement this call: game.getChessboard(gameID)

        //TODO: this is not nice, use delegation
        return game.chessboard.squares;
    }
}
