package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.ExecutedMoveController;

/**
 * This class is send as a response from the {@link ExecutedMoveController}.
 *
 * It contains the following fields: <br>
 *
 *
 * @author Hannes Stuetzer
 */
public class ExecutedMoveObject {
    private int gameID;
    private int[] previousPiecePosition;
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
