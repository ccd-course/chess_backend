package com.chess.backend.services;

import com.chess.backend.domain.models.IGame;
import com.chess.backend.domain.services.IGameService;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.pieces.Piece;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game service to initialize new Game and do operations on it
 */
@Component
public class ChessGameService implements IGameService {
    private final PlayerService playerService = new PlayerService();

    private static final ChessGameService CHESS_GAME_SERVICE = new ChessGameService();

    private ChessGame game;

    /**
     * generate new ID for a game object
     *
     * @return id
     */
    @Override
    public Integer getNewGameID() {
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1);
    }

    /**
     * get current game service instance
     *
     * @return GameService instance
     */
    public static ChessGameService getInstance() {
        return CHESS_GAME_SERVICE;
    }

    public ChessGameService() {
    }

    public ChessGame createNewOnlineGame(String[] playerNames) {
        game = new ChessGame();

        //getting and setting the gameID
        game.setId(this.getNewGameID());

        //initialize the players
        List<Player> players = playerService.initPlayers(playerNames);
        game.setPlayers(players);
        game.setActivePlayer(players.get(0));

        //initialize the chessboard

        Chessboard newGameChessboard = ChessboardService.initNewGameBoard(game.getPlayers());
        game.setChessboard(newGameChessboard);
        return game;
    }

        /**
         * create new Game
         *
         * @param playerNames players names
         * @return boolean that's true if game is created
         */
    public boolean createNewGame(String[] playerNames) {
        game = new ChessGame();

        //getting and setting the gameID
        game.setId(this.getNewGameID());

        //initialize the players
        List<Player> players = playerService.initPlayers(playerNames);
        game.setPlayers(players);
        game.setActivePlayer(players.get(0));

        //initialize the chessboard
        Chessboard newGameChessboard = ChessboardService.initNewGameBoard(game.getPlayers());
        game.setChessboard(newGameChessboard);

        return true;
    }

    @Override
    public ChessGame getGame() {
        return game;
    }

    @Override
    public int getGameID() {
        return game.getId();
    }

    public ArrayList<ArrayList<Square>> getBoard(int gameID) {
        if (verifyGameID(gameID)) {
            return getAllSquaresFromChessboard();
        } else {
            return null;
        }
    }
    public ArrayList<ArrayList<Square>> getAllSquaresFromChessboard(){
        return this.game.getChessboard().getSquares();
    }

    /**
     * check if piece can move to the target position
     *
     * @param gameID                game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition      [x,y] position
     * @return true if valid
     */
    @Override
    public boolean validateMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition) {
        int[][] possibleMoves = getPossibleMoves(gameID, previousPiecePosition);

        for (int[] possibleMove : possibleMoves) {
            if ((possibleMove[0] == newPiecePosition[0]) && (possibleMove[1] == newPiecePosition[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     * returns possible moves for a piece position
     * return value looks like that
     * [[x,y], [2,2], [2,3], [3,3]]
     *
     * @param gameID        game id
     * @param piecePosition position of a piece
     * @return 2D array of possible positions [x,y]
     */
    @Override
    public int[][] getPossibleMoves(int gameID, int[] piecePosition) {
        if (verifyGameID(gameID)) {
            Piece piece = ChessboardService.getPieceByPosition(game.getChessboard(), piecePosition[0], piecePosition[1]);
            ArrayList<Square> moveList = piece.getAllowedMoves(game);
            int[][] moves = new int[moveList.size()][2];

            for (int i = 0; i < moveList.size(); i++) {
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
     *
     * @param gameID                game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition      [x,y] position
     * @return if verified game id and valid move return the Activate player name otherwise return empty string
     */
    @Override
    public String executedMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition) {
        if (verifyGameID(gameID)) {
            if (this.validateMove(gameID, previousPiecePosition, newPiecePosition)) {
                ChessboardService.move(game.getChessboard(), previousPiecePosition[0], previousPiecePosition[1], newPiecePosition[0], newPiecePosition[1]);
                this.switchActive(game);

                return getActivePlayerName();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
    public String getActivePlayerName(){
        return this.game.getActivePlayer().getName();
    }


    /**
     * Method to switch active players after move
     */
    private void switchActive(IGame game) {
        List<Player> players = game.getPlayers();
        Player activePlayer = game.getActivePlayer();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(activePlayer)) {
                if (i == (players.size() - 1)) {
                    game.setActivePlayer(players.get(0));
                    break;
                } else {
                    game.setActivePlayer(players.get(i + 1));
                    break;
                }
            }
        }
    }

    /**
     * returns activate player
     *
     * @param gameID game id
     * @return player name
     */
    @Override
    public String getPlayerTurn(int gameID) {
        if (verifyGameID(gameID)) {
            return getActivePlayerName();
        } else {
            return "";
        }
    }

    /**
     * End game function
     *
     * @param gameID game id
     * @return true if game is valid and ended successfully
     */
    @Override
    public boolean endGame(int gameID) {
        if (verifyGameID(gameID)) {
            game = null;
            System.gc();

            return true;
        } else {
            return false;
        }
    }

}
