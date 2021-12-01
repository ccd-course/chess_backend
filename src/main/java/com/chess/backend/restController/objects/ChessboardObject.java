package com.chess.backend.restController.objects;

public class ChessboardObject {
    private int gameID;
    private Object[][] chessboard;

    public ChessboardObject(int gameID, Object[][] chessboard){
        this.gameID = gameID;
        this.chessboard = chessboard;
    }

    public int getGameID() {
        return gameID;
    }

    public Object[][] getChessboard() {
        return chessboard;
    }
}
