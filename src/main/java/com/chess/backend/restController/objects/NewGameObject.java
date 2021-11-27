package com.chess.backend.restController.objects;

/**
 * This class is send as response from the {@link com.chess.backend.restController.controller.NewGameController}.
 *
 * It contains the following fields: <br>
 * {@link gameID} - id of the current game. <br>
 * {@link chessboard} - the new generated chessboard.
 *
 * @author Hannes Stuetzer
 */
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
