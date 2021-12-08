package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.ExecutedMoveController;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @Schema(description = "Game ID", example = "2")
    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    @ArraySchema(schema = @Schema(description = "Piece position [x, y]", example = "0"), minItems = 2, maxItems = 2)
    public int[] getPreviousPiecePosition() {
        return previousPiecePosition;
    }

    public void setPreviousPiecePosition(int[] previousPiecePosition) {
        this.previousPiecePosition = previousPiecePosition;
    }

    @ArraySchema(schema = @Schema(description = "Piece position [x, y]", example = "0"), minItems = 2, maxItems = 2)
    public int[] getNewPiecePosition() {
        return newPiecePosition;
    }

    public void setNewPiecePosition(int[] newPiecePosition) {
        this.newPiecePosition = newPiecePosition;
    }
}
