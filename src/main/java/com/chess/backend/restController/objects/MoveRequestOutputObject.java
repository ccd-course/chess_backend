package com.chess.backend.restController.objects;

public class MoveRequestOutputObject {
    private int[][] possibleMoves;

    public MoveRequestOutputObject(int[][] possibleMoves){
        this.possibleMoves = possibleMoves;
    }

    public int[][] getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(int[][] possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
