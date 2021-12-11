package com.chess.backend.domain.services;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Square;

public interface IGameService {
    /**
     * generate new ID for a game object
     *
     * @return id
     */
    Integer getNewGameID();

    Game getGame();

    Square[][] getBoard(int gameID);

    int getGameID();


    /**
     * returns possible moves for a piece position
     * return value looks like that
     * [[x,y], [2,2], [2,3], [3,3]]
     *
     * @param gameID        game id
     * @param piecePosition position of a piece
     * @return 2D array of possible positions [x,y]
     */
    int[][] getPossibleMoves(int gameID, int[] piecePosition);

    /**
     * move piece from one position to another
     *
     * @param gameID                game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition      [x,y] position
     * @return if verified game id and valid move return the Activate player name otherwise return empty string
     */
    String executedMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition);

    /**
     * check if piece can move to the target position
     *
     * @param gameID                game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition      [x,y] position
     * @return true if valid
     */
    default boolean validateMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition) {
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
     *
     * @param gameID game id
     * @return player name
     */
    String getPlayerTurn(int gameID);

    /**
     * End game function
     *
     * @param gameID game id
     * @return true if game is valid and ended successfully
     */
    boolean endGame(int gameID);

    /**
     * check if game id is valid
     *
     * @param gameID game id
     * @return true if valid
     */
    default boolean verifyGameID(int gameID) {
        return gameID == getGameID();
    }
}
