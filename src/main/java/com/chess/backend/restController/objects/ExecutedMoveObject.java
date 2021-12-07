package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.ExecutedMoveController;

/**
 * This class is received from the API-Call of the {@link ExecutedMoveController}.
 *
 * @author Hannes Stuetzer
 */
public class ExecutedMoveObject {
    /**
     * the id of the current game.
     */
    private int gameID;
    /**
     * the old position of the chess piece.
     */
    private int[] previousPiecePosition;
    /**
     * the new position of the chess piece.
     */
    private int[] newPiecePosition;

    public ExecutedMoveObject(int gameID, int[] previousPiecePosition, int[] newPiecePosition){
        this.gameID = gameID;
        this.previousPiecePosition = previousPiecePosition;
        this.newPiecePosition = newPiecePosition;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int[] getPreviousPiecePosition() {
        return previousPiecePosition;
    }

    public void setPreviousPiecePosition(int[] previousPiecePosition) {
        this.previousPiecePosition = previousPiecePosition;
    }

    public int[] getNewPiecePosition() {
        return newPiecePosition;
    }

    public void setNewPiecePosition(int[] newPiecePosition) {
        this.newPiecePosition = newPiecePosition;
    }
}
