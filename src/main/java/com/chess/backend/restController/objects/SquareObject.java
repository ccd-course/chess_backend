package com.chess.backend.restController.objects;

/**
 * This class represents one simple square of the chessboard ({@link ChessboardObject}).
 *
 * @author Hannes Stuetzer
 */
public class SquareObject {
    /**
     * the name of a chess piece
     */
    private String pieceID;
    /**
     * the name of the player who owns the chess piece
     */
    private String playerName;

    public SquareObject(String pieceID, String playerName){
        this.pieceID = pieceID;
        this.playerName = playerName;
    }

    public String getPieceID() {
        return pieceID;
    }

    public void setPieceID(String pieceID) {
        this.pieceID = pieceID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
