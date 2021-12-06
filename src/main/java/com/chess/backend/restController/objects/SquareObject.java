package com.chess.backend.restController.objects;

public class SquareObject {
    private String pieceID;
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
