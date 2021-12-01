package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.VerifyExecutedMoveController;

/**
 * This class is send as a response from the {@link VerifyExecutedMoveController}.
 *
 * It contains the following fields: <br>
 *
 *
 * @author Hannes Stuetzer
 */
public class VerifyExecutedMoveObject {
    private int gameID;
    private boolean isMoveValid;
    private Object[][] chessboard;

    public VerifyExecutedMoveObject(int gameID, boolean isMoveValid, Object[][] chessboard){
        this.gameID = gameID;
        this.isMoveValid = isMoveValid;
        this.chessboard = chessboard;
    }

    public int getGameID() {
        return gameID;
    }

    public boolean getIsMoveValid() {
        return isMoveValid;
    }

    public Object[][] getChessboard() {
        return chessboard;
    }
}
