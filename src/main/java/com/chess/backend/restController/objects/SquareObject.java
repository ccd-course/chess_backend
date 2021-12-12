package com.chess.backend.restController.objects;

import lombok.Data;

/**
 * This class represents one simple square of the chessboard ({@link ChessboardObject}).
 *
 * @author Hannes Stuetzer
 */
@Data
public class SquareObject {
    /**
     * the name of a chess piece
     */
    private String pieceID;
    /**
     * the name of the player who owns the chess piece
     */
    private String playerName;

    public SquareObject(String pieceID, String playerName) {
        this.pieceID = pieceID;
        this.playerName = playerName;
    }
}
