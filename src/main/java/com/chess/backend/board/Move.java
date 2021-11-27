package com.chess.backend.board;
import com.chess.backend.pieces.Piece;

public class Move {
    private Chessboard chessboard;
    private Piece movedPiece;
    private int destination;

    public Move(Chessboard chessboard, Piece movedPiece, int destination){
        this.chessboard = chessboard;
        this.movedPiece = movedPiece;
        this.destination = destination;
    }

    // To be implemented
    // The chessboard will be manipulated
    public void makeMove(){}
}
