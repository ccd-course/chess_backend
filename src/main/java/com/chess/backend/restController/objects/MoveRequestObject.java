package com.chess.backend.restController.objects;

public class MoveRequestObject {
    private int pieceID;
    //[x, y]
    private int[] initialPlace;
    private int[] possibleMoves;

    public MoveRequestObject(int pieceID, int[] initialPlace, int[] possibleMoves){
        this.pieceID = pieceID;
        this.initialPlace = initialPlace;
        this.possibleMoves = possibleMoves;
    }

    public int getPieceID() {
        return pieceID;
    }

    public void setPieceID(int pieceID) {
        this.pieceID = pieceID;
    }

    public int[] getInitialPlace() {
        return initialPlace;
    }

    public void setInitialPlace(int[] initialPlace) {
        this.initialPlace = initialPlace;
    }

    public int[] getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(int[] possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
