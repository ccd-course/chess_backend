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
    private int gameID;
    private String pieceID;
    //[x,y]
    private int[] initialPlace;
    //[[x,y], [x,y], [x,y]]
    private int[][] possibleMoves;

    public MoveRequestObject(int gameID, String pieceID, int[] initialPlace, int[][] possibleMoves){
        this.gameID = gameID;
        this.pieceID = pieceID;
        this.initialPlace = initialPlace;
        this.possibleMoves = possibleMoves;
    }

    public int getGameID() { return gameID; }

    public String getPieceID() {
        return pieceID;
    }

    public int[] getInitialPlace() {
        return initialPlace;
    }

    public int[][] getPossibleMoves() {
        return possibleMoves;
    }
}
