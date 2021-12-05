package com.chess.backend.services;

import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.Square;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {
    @Autowired
    private ChessboardService chessboardService;
    private PlayerService playerService = new PlayerService();
    private GameIDService gameIDService = new GameIDService();

    private static final GameService gameService = new GameService();
    private Game game;

    public static GameService getInstance() {
        return gameService;
    }

    public boolean createNewGame(String[] playerNames){
        game = new Game();

        //getting and setting the gameID
        game.setId(gameIDService.getNewGameID());

        //initialize the players
        Player[] players = playerService.initPlayers(playerNames);
        game.setPlayers(players);
        game.setActivePlayer(players[0]);

        //initialize the chessboard
        Chessboard newGameChessboard = chessboardService.initNewGameBoard(playerNames);
        game.setChessboard(newGameChessboard);

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
            return game.getChessboard().getSquares();
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
