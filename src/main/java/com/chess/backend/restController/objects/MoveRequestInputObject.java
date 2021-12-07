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
public class MoveRequestInputObject {
    private int gameID;
    //[x,y]
    private int[] piecePosition;

    public MoveRequestInputObject(int gameID, int[] piecePosition){
        this.gameID = gameID;
        this.piecePosition = piecePosition;
    }

    public int getGameID() { return gameID; }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int[] getInitialPlace() {
        return piecePosition;
    }

    public int[] getPiecePosition() {
        return piecePosition;
    }
}
