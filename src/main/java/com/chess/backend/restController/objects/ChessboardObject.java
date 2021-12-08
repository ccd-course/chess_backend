package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.GetChessboardController;

/**
 * This class represents a simple version of the chessboard, which send as a response of the API-Call of {@link GetChessboardController}.
 *
 * @author Hannes Stuetzer
 */
public class ChessboardObject {
    /**
     * holds the chessboard.
     */
    private SquareObject[][] chessboard;

    public ChessboardObject(SquareObject[][] chessboard) {
        this.chessboard = chessboard;
    }

    public SquareObject[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(SquareObject[][] chessboard) {
        this.chessboard = chessboard;
    }
}
