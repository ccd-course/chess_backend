package com.chess.backend.restController.objects;

public class NewGameObject {
    private int gameID;
    private Object[][] chessboard;

    public NewGameObject(int gameID, Object[][] chessboard){
        this.gameID = gameID;
        this.chessboard = chessboard;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Object[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(Object[][] chessboard) {
        this.chessboard = chessboard;
    }
}
