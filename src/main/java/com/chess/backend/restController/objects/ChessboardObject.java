package com.chess.backend.restController.objects;

public class ChessboardObject {
    private int gameID;
    private SquareObject[][] chessboard;

    public ChessboardObject(int gameID, SquareObject[][] chessboard){
        this.gameID = gameID;
        this.chessboard = chessboard;
    }

    public int getGameID() {
        return gameID;
    }

    public SquareObject[][] getChessboard() {
        return chessboard;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setChessboard(SquareObject[][] chessboard) {
        this.chessboard = chessboard;
    }
}
