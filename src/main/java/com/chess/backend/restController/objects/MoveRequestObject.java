package com.chess.backend.restController.objects;

/**
 * This class is send as a response from the {@link com.chess.backend.restController.controller.MoveRequestController}.
 *
 * It contains the following fields: <br>
 * {@link pieceID} - the ID of the piece <br>
 * {@link initialPlace} - the square where the piece is standing as int[] with [x, y] <br>
 * {@link possibleMoves} - an array of all possible moves of the piece
 *
 * @author Hannes Stuetzer
 */
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
