package com.chess.backend.services;

import com.chess.backend.domain.models.IGame;
import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.domain.services.IGameService;
import com.chess.backend.domain.services.INewGameService;
import com.chess.backend.gamemodel.*;
import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.constants.Event;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private IGameRepository gameRepository;

    @Autowired
    public ChessGameService(@Qualifier("GameRepositoryClass") IGameRepository gameRepository ){
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
        ChessboardService.setCommonPiecePlayer(game.getChessboard(), PieceType.CANNON, game.getActivePlayer());
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
        ChessboardService.setCommonPiecePlayer(game.getChessboard(), PieceType.CANNON, game.getActivePlayer());

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
            ArrayList<Square> moveList = ChessboardService.getValidMovesForPiece(game.getChessboard(), piece, game.getActivePlayer());
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
            checkEndingConditions(game);
            List<Event> events = game.getEvents();
            events.add(Event.NEW_MOVE);
            game.setEvents(events);
            this.gameRepository.createNewGame(game.getId(), game);
            return getActivePlayerName(game);
        } else {
            return "";
        }

    }

    /**
     * Get the name of the active player.
     * @return The name of the active player.
     */
    public String getActivePlayerName(ChessGame game){
        return game.getActivePlayer().getName();
    }

    /**
     * Checks if an ending condition to end the game is fulfilled.
     * @param game The game context.
     */
    private void checkEndingConditions(ChessGame game){
        // the players king can be captured and the player has no valid move
        if(ChessboardService.isCheck(game.getChessboard(), game.getActivePlayer()) && !ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getActivePlayer())){
            // the game is over
            // the active player has lost
            // the next player in the move order who can capture the players king wins
            List<Event> events = game.getEvents();
            events.add(Event.CHECKMATED);
            game.setEvents(events);
            game.setWinner(determineWinnerByMoveOrder(game, game.getActivePlayer()));
        } else {
            // the players king can not be captured, but the player has no valid move
            if(!ChessboardService.isCheck(game.getChessboard(), game.getActivePlayer()) && !ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getActivePlayer())){
                // the game ends in a draw
                // no player has won or lost
                List<Event> events = game.getEvents();
                events.add(Event.DRAW);
                game.setEvents(events);
            } else {
                // the player can capture an opponent king
                ArrayList<Player> capturedPlayers = ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getActivePlayer());
                if(capturedPlayers.size() > 0){
                    // the game ends because the active player ca capture an opponent king
                    // the active player wins
                    // the captured players loose
                    List<Event> events = game.getEvents();
                    events.add(Event.CHECKMATED);
                    game.setEvents(events);
                    game.setWinner(game.getActivePlayer());
                    //ArrayList<Player> loosers = capturedPlayers;
                }
            }
        }
    }

    /**
     * Determines the winner if a player is checkmate.
     *
     * @param game   The game context.
     * @param looser The player who is checkmate.
     * @return The winning player (the next player in the move order who can capture the king of the looser).
     */
    private Player determineWinnerByMoveOrder(IGame game, Player looser){
        List<Player> playerOrder = game.getPlayers();

        while(playerOrder.get(0) != looser){
            playerOrder.add(playerOrder.get(0));
            playerOrder.remove(0);
        }

        playerOrder.remove(0);

        for(Player player : playerOrder){
            for(Player capturedPlayer : ChessboardService.getCaptureKingPlayers(game.getChessboard(), player)){
                if(capturedPlayer.getColor() == looser.getColor()){
                    return player;
                }
            }
        }

        return null;
    }

    /**
     * Method to switch active players after move
     */
    public static void switchActive(ChessGame game) {
        List<Player> players = game.getPlayers();
        Player activePlayer = game.getActivePlayer();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(activePlayer)) {
                if (i == (players.size() - 1)) {
                    setActivePlayer(game, players.get(0));
                    break;
                } else {
                    setActivePlayer(game, players.get(i + 1));
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


    public static void setActivePlayer (ChessGame chessGame, Player activePlayer){
        chessGame.setActivePlayer(activePlayer);
        ChessboardService.setCommonPiecePlayer(chessGame.getChessboard(), PieceType.CANNON, activePlayer);
    }

}
