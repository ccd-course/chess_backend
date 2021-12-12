package com.chess.backend.restController.objects;

import com.chess.backend.restController.controller.GetChessboardController;
import lombok.Data;

/**
 * This class represents a simple version of the chessboard, which send as a response of the API-Call of {@link GetChessboardController}.
 *
 * @author Hannes Stuetzer
 */
@Data
public class ChessboardObject {
    /**
     * holds the chessboard.
     */
    private SquareObject[][] chessboard;

    public ChessboardObject(SquareObject[][] chessboard) {
        this.chessboard = chessboard;
    }
}
