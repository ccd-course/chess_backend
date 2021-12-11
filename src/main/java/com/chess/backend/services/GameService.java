package com.chess.backend.services;

import com.chess.backend.gamemodel.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game service to initialize new Game and do operations on it
 */
@Component
public class GameService {
    private final PlayerService playerService = new PlayerService();

    private static final GameService gameService = new GameService();

    private Game game;

    /**
     * get current game service instance
     * @return GameService instance
     */
    public static GameService getInstance() {
        return gameService;
    }

    public GameService() {
    }

    /**
     * generate new ID for a game object
     * @return id
     */
    private Integer getNewGameID(){
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1);
    }

    /**
     * create new Game
     * @param playerNames players names
     * @return boolean that's true if game is created
     */
    public boolean createNewGame(String[] playerNames) {
        game = new Game();

        //getting and setting the gameID
        game.setId(this.getNewGameID());

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
            return this.game.getAllSquaresFromChessboard();
        } else {
            return null;
        }
    }

    /**
     * returns possible moves for a piece position
     *         return value looks like that
     *         [[x,y], [2,2], [2,3], [3,3]]
     * @param gameID game id
     * @param piecePosition position of a piece
     * @return 2D array of possible positions [x,y]
     */
    public int[][] getPossibleMoves(int gameID, int[] piecePosition){
        if(verifyGameID(gameID)){
            Piece piece = ChessboardService.getPieceByPosition(game.getChessboard(), piecePosition[0], piecePosition[1]);
            ArrayList<Square> moveList = piece.getAllowedMoves(game);

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

    /**
     * move piece from one position to another
     * @param gameID game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition [x,y] position
     * @return if verified game id and valid move return the Activate player name otherwise return empty string
     */
    public String executedMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition){
        if(verifyGameID(gameID)){
            if(validateMove(gameID, previousPiecePosition, newPiecePosition)){
                ChessboardService.move(game.getChessboard(), previousPiecePosition[0], previousPiecePosition[1], newPiecePosition[0], newPiecePosition[1]);
                game.switchActive();

                return game.getActivePlayerName();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * check if piece can move to the target position
     * @param gameID game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition [x,y] position
     * @return true if valid
     */
    private boolean validateMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition){
        int[][] possibleMoves = getPossibleMoves(gameID, previousPiecePosition);

        for (int[] possibleMove : possibleMoves) {
            if ((possibleMove[0] == newPiecePosition[0]) && (possibleMove[1] == newPiecePosition[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     * returns activate player
     * @param gameID game id
     * @return player name
     */
    public String getPlayerTurn(int gameID) {
        if (verifyGameID(gameID)) {
            return game.getActivePlayerName();
        } else {
            return "";
        }
    }

    /**
     * End game function
     * @param gameID game id
     * @return true if game is valid and ended successfully
     */
    public boolean endGame(int gameID){
        if(verifyGameID(gameID)){
            game = null;
            System.gc();

            return true;
        } else {
            return false;
        }
    }

    /**
     * check if game id is valid
     * @param gameID game id
     * @return true if valid
     */
    private boolean verifyGameID(int gameID) {
        return gameID == getGameID();
    }
}
