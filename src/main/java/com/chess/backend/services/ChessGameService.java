package com.chess.backend.services;

import com.chess.backend.domain.models.IGame;
import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Event;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.repository.metadata.EventMetadata;
import com.chess.backend.repository.metadata.EventObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game service to initialize new Game and do operations on it.
 */
@Service
public class ChessGameService {
    private final IGameRepository gameRepository;

    @Autowired
    public ChessGameService(@Qualifier("GameRepositoryClass") IGameRepository gameRepository ){
        this.gameRepository = gameRepository;
    }

//    private static final ChessGameService CHESS_GAME_SERVICE = new ChessGameService();


    /**
     * Generate new ID for a game object
     *
     * @return id
     */
    public static Integer getNewGameID() {
        return ThreadLocalRandom.current().nextInt(1, 1000 + 1);
    }

    /**
     * create new Game
     *
     * @param playerNames players names
     * @return ChessGame
     */
    public static ChessGame createNewGame(String[] playerNames) {
        ChessGame game = new ChessGame();

        //getting and setting the gameID
        game.setId(getNewGameID());

        //initialize the players
        List<Player> players = PlayerService.initPlayers(playerNames);
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
     * Returns a game object by gameID
     *
     * @param gameID The gameID key under which the game object is stored in the gameRepository
     * @return ChessGame
     */
    public ChessGame getGame(Integer gameID) {
        return gameRepository.getGame(gameID);

    }


    /**
     * Returns the ChessBoard by gameID
     *
     * @param gameID The gameID key under which the game object is stored in the gameRepository
     * @return ChessBoard nested ArrayList
     */
    public ArrayList<ArrayList<Square>> getBoard(int gameID) {
        ChessGame game = this.getGame(gameID);
        return getAllSquaresFromChessboard(game);
    }

    /**
     * Returns the ChessBoard of a game object
     *
     * @param game A game object
     * @return ChessBoard nested ArrayList
     */
    public static ArrayList<ArrayList<Square>> getAllSquaresFromChessboard(ChessGame game){
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
     * @param game A game object
     * @param piecePosition Integer array representing a position
     * @return Two-dimensional integer array of moves.
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
            List<EventObject> events = new ArrayList<>();
            events.addAll(ChessboardService.move(game.getChessboard(), previousPiecePosition[0], previousPiecePosition[1], newPiecePosition[0], newPiecePosition[1]));
            switchActive(game);
            List<EventObject> endingConditionEvents  = checkEndingConditions(game);
            EventMetadata switchPlayerMetaData = new EventMetadata(game.getActivePlayer().getId(), game.getActivePlayer().getName());
            EventObject switchPlayerEvent = new EventObject(Event.PLAYER_CHANGE, switchPlayerMetaData);

            events.add(switchPlayerEvent);
            events.addAll(endingConditionEvents);
            this.gameRepository.updateGame(game.getId(), game, events);
            return getActivePlayerName(game);
        } else {
            return "";
        }

    }

    /**
     * Get the name of the active player.
     * @return The name of the active player.
     */
    public static String getActivePlayerName(ChessGame game){
        return game.getActivePlayer().getName();
    }

    /**
     * Checks if an ending condition to end the game is fulfilled.
     * @param game The game context.
     */
    private List<EventObject> checkEndingConditions(ChessGame game){
        // the players king can be captured and the player has no valid move
        List<EventObject> events = new ArrayList<>();

        if(ChessboardService.isCheck(game.getChessboard(), game.getActivePlayer()) && !ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getActivePlayer())){
            // the game is over
            // the active player has lost
            // the next player in the move order who can capture the players king wins
            Player winner = determineWinnerByMoveOrder(game, game.getActivePlayer());
            events.add(new EventObject(Event.CHECKMATED,  new EventMetadata(winner.getId(), winner.getName())));
            game.setEvents(events);
            game.setWinner(winner);
        } else {
            // the players king can not be captured, but the player has no valid move
            if(!ChessboardService.isCheck(game.getChessboard(), game.getActivePlayer()) && !ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getActivePlayer())){
                // the game ends in a draw
                // no player has won or lost
                events.add(new EventObject(Event.DRAW));
            } else {
                // the player can capture an opponent king
                ArrayList<Player> capturedPlayers = ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getActivePlayer());
                if(capturedPlayers.size() > 0){
                    // the game ends because the active player ca capture an opponent king
                    // the active player wins
                    // the captured players loose
                    events.add(new EventObject(Event.CHECKMATED, new EventMetadata(game.getActivePlayer().getId(), game.getActivePlayer().getName())));
                    game.setEvents(events);
                    game.setWinner(game.getActivePlayer());
                    //ArrayList<Player> loosers = capturedPlayers;
                }
            }
        }
        return events;
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
     * @param game Game object
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


    /**
     * Helper method to set the active player and update all cannon pieces.
     *
     * @param chessGame Game object
     * @param activePlayer New active player
     */
    public static void setActivePlayer (ChessGame chessGame, Player activePlayer){
        chessGame.setActivePlayer(activePlayer);
        ChessboardService.setCommonPiecePlayer(chessGame.getChessboard(), PieceType.CANNON, activePlayer);
    }

}
