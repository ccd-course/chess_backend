package com.chess.backend.services;

import com.chess.backend.domain.models.IGame;
import com.chess.backend.domain.services.IGameService;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game service to initialize new Game and do operations on it
 */
@Service
public class ChessGameService {
    private final PlayerService playerService = new PlayerService();
    private GameRepository gameRepository;

    @Autowired
    public ChessGameService(GameRepository gameRepository ){
        this.gameRepository = gameRepository;
    }

//    private static final ChessGameService CHESS_GAME_SERVICE = new ChessGameService();


    /**
     * generate new ID for a game object
     *
     * @return id
     */
    public Integer getNewGameID() {
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1);
    }


    /**
     *
     * @param playerNames
     * @return
     */
    public ChessGame createNewOnlineGame(String[] playerNames) {
        ChessGame game = new ChessGame();

        //getting and setting the gameID
        game.setId(this.getNewGameID());
        System.out.println("PLAYERS: "+ playerNames[0]);
        //initialize the players
        List<Player> players = playerService.initPlayers(playerNames);
        game.setPlayers(players);
        game.setActivePlayer(players.get(0));
        game.setEvents(new ArrayList<>());

        //initialize the chessboard

        Chessboard newGameChessboard = ChessboardService.initNewGameBoard(game.getPlayers());
        game.setChessboard(newGameChessboard);
        return game;
    }

        /**
         * create new Game
         *
         * @param playerNames players names
         * @return ChessGame
         */
    public ChessGame createNewGame(String[] playerNames) {
        ChessGame game = new ChessGame();

        //getting and setting the gameID
        game.setId(this.getNewGameID());

        //initialize the players
        List<Player> players = playerService.initPlayers(playerNames);
        game.setPlayers(players);
        game.setActivePlayer(players.get(0));
        game.setEvents(new ArrayList<>());

        //initialize the chessboard
        Chessboard newGameChessboard = ChessboardService.initNewGameBoard(game.getPlayers());
        game.setChessboard(newGameChessboard);

        return game;
    }


    public ChessGame getGame(Integer gameId) {
        return gameRepository.getGame(gameId);

    }

    public ArrayList<ArrayList<Square>> getBoard(int gameID) {
        ChessGame game = this.getGame(gameID);
        return getAllSquaresFromChessboard(game);
    }
    public ArrayList<ArrayList<Square>> getAllSquaresFromChessboard(ChessGame game){
        return game.getChessboard().getSquares();
    }

    /**
     * check if piece can move to the target position
     *
     * @param gameID                game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition      [x,y] position
     * @return true if valid
     */
    public boolean validateMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition) {
        ChessGame game = this.getGame(gameID);
        int[][] possibleMoves = getPossibleMoves(game, previousPiecePosition);
        for (int[] possibleMove : possibleMoves) {
            if ((possibleMove[0] == newPiecePosition[0]) && (possibleMove[1] == newPiecePosition[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     *      returns possible moves for a piece position
     *      return value looks like that
     *      [[x,y], [2,2], [2,3], [3,3]]
     * @param game
     * @param piecePosition
     * @return
     */
    public int[][] getPossibleMoves(ChessGame game, int[] piecePosition) {
            Piece piece = ChessboardService.getPieceByPosition(game.getChessboard(), piecePosition[0], piecePosition[1]);
            ArrayList<Square> moveList = piece.getAllowedMoves(game);
            int[][] moves = new int[moveList.size()][2];

            for (int i = 0; i < moveList.size(); i++) {
                moves[i][0] = moveList.get(i).getPosX();
                moves[i][1] = moveList.get(i).getPosY();
            }

            return moves;
    }

    /**
     * move piece from one position to another
     *
     * @param gameID                game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition      [x,y] position
     * @return if verified game id and valid move return the Activate player name otherwise return empty string
     */
    public String executedMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition) {
        ChessGame game = this.getGame(gameID);
        if (this.validateMove(gameID, previousPiecePosition, newPiecePosition)) {
            ChessboardService.move(game.getChessboard(), previousPiecePosition[0], previousPiecePosition[1], newPiecePosition[0], newPiecePosition[1]);
            this.switchActive(game);

            return getActivePlayerName(game);
        } else {
            return "";
        }

    }
    public String getActivePlayerName(ChessGame game){
        return game.getActivePlayer().getName();
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
    public String getPlayerTurn(int gameID) {
        ChessGame game = this.getGame(gameID);
        return getActivePlayerName(game);
    }


}
