package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.MoveRequestController;

/**
 * This class is created via API-Call of the {@link MoveRequestController}.
 *
 * @author Hannes Stuetzer
 */
public class MoveRequestInputObject {
    /**
     * the id of the current game.
     */
    private int gameID;
    /**
     * the position of the chess piece from which the moves should be calculated (format: [x,y]).
     */
    private int[] piecePosition;

    public MoveRequestInputObject(int gameID, int[] piecePosition){
        this.gameID = gameID;
        this.piecePosition = piecePosition;
    }

    public int getGameID() { return gameID; }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int[] getInitialPlace() {
        return piecePosition;
    }

    public int[] getPiecePosition() {
        return piecePosition;
    }
}
