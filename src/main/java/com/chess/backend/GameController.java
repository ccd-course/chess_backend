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
        if (verifyGameID(gameID)) {
            //TODO: handle the getting of the chessboard with the gameID
            //TODO: implement this call: game.getChessboard(gameID)

            //TODO: this is not nice, use delegation
            return game.chessboard.squares;
        } else {
            return null;
        }
    }

    /*
        return value looks like that
        [[x,y], [2,2], [2,3], [3,3]]
     */
    public int[][] getPossibleMoves(int gameID, String pieceID, int[] piecePosition){
        if(verifyGameID(gameID)){
            //TODO: get the possible moves
            return new int[][]{};
        } else {
            return new int[][]{};
        }
    }

    public boolean verifyExecutedMove(int gameID, String pieceID, int[] previousPiecePosition, int[] newPiecePosition){
        if(verifyGameID(gameID)){
            //TODO: implement the checking of a move that was executed in the front end
            return true;
        } else {
            return false;
        }
    }

    public String getPlayerTurn(int gameID){
        if(verifyGameID(gameID)){
            //TODO: get the player who has his turn right now

            return "Hannes";
        } else {
            return "";
        }
    }

    private boolean verifyGameID(int gameID){
        if(gameID == getGameID()){
            return true;
        } else {
            return false;
        }
    }
}
