package com.chess.backend.restController.objects;

public class ChessboardObject {
    private SquareObject[][] chessboard;

    public ChessboardObject(SquareObject[][] chessboard){
        this.chessboard = chessboard;
    }

    public SquareObject[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(SquareObject[][] chessboard) {
        this.chessboard = chessboard;
    }
}
