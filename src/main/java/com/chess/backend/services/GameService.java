package com.chess.backend.services;

import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.Square;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GameService {
    private PlayerService playerService = new PlayerService();
    private GameIDService gameIDService = new GameIDService();

    private static final GameService gameService = new GameService();

    private Game game;

    public static GameService getInstance() {
        return gameService;
    }

    public GameService() {
    }

    public boolean createNewGame(String[] playerNames) {
        game = new Game();

        //getting and setting the gameID
        game.setId(gameIDService.getNewGameID());

        //initialize the players
        Player[] players = playerService.initPlayers(playerNames);
        game.setPlayers(players);
        game.setActivePlayer(players[0]);

        //initialize the chessboard
        Chessboard newGameChessboard = ChessboardService.initNewGameBoard(game.getPlayers());
        game.setChessboard(newGameChessboard);

        return true;
    }

    public Game getGame() {
        return game;
    }

    public int getGameID() {
        return game.getId();
    }

    public Square[][] getChessboard(int gameID) {
        if (verifyGameID(gameID)) {
            //TODO: handle the getting of the chessboard with the gameID
            //TODO: implement this call: game.getChessboard(gameID)

            return game.getChessboard().getSquares();
        } else {
            return null;
        }
    }

    /*
        return value looks like that
        [[x,y], [2,2], [2,3], [3,3]]
     */
    public int[][] getPossibleMoves(int gameID, int[] piecePosition){
        if(verifyGameID(gameID)){
            //TODO: resolve this, use delegation
            //we need a method getPieceByPosition(int x, int y) in game or in the chessboardService
            ArrayList<Square> moveList = game.getChessboard().getSquares()[piecePosition[0]][piecePosition[1]].getPiece().getAllowedMoves(game);
            int[][] moves = new int[moveList.size()][2];

            for(int i = 0; i < moveList.size(); i++){
                moves[i][0] = moveList.get(i).getPosX();
                moves[i][1] = moveList.get(i).getPosY();
            }

            return moves;
        } else {
            return new int[][]{};
        }
    }

    public boolean executedMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition){
        if(verifyGameID(gameID)){
            if(validateMove(gameID, previousPiecePosition, newPiecePosition)){
                ChessboardService.move(game.getChessboard(), previousPiecePosition[0], previousPiecePosition[1], newPiecePosition[0], newPiecePosition[1]);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean validateMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition){
        int[][] possibleMoves = getPossibleMoves(gameID, previousPiecePosition);

        for(int i = 0; i < possibleMoves.length; i++){
            if((possibleMoves[i][0] == newPiecePosition[0]) && (possibleMoves[i][1] == newPiecePosition[1])){
                return true;
            }
        }

        return false;
    }

    public String getPlayerTurn(int gameID) {
        if (verifyGameID(gameID)) {
            return game.getActivePlayer().getName();
        } else {
            return "";
        }
    }

    public boolean endGame(int gameID){
        if(verifyGameID(gameID)){
            game = null;
            System.gc();

            return true;
        } else {
            return false;
        }
    }

    private boolean verifyGameID(int gameID) {
        if (gameID == getGameID()) {
            return true;
        } else {
            return false;
        }
    }
}
