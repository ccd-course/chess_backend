package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.MoveRequestController;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class is send as a response from the API-Call of the {@link MoveRequestController}.
 *
 * @author Hannes Stuetzer
 */
public class MoveRequestOutputObject {
    /**
     * contains the possible moves as an array of positions (format: [[x,y], [x,y], ...]).
     */
    private int[][] possibleMoves;

    public MoveRequestOutputObject(int[][] possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    @ArraySchema(arraySchema = @Schema(type = "array"), schema = @Schema(description = "Piece position [x, y]", example = "[0, 0]"), minItems = 0)
    public int[][] getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(int[][] possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
