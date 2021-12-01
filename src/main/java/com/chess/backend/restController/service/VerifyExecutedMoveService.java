package com.chess.backend.restController.service;

import com.chess.backend.GameController;
import com.chess.backend.restController.objects.VerifyExecutedMoveObject;
import org.springframework.stereotype.Service;

@Service
public class VerifyExecutedMoveService {
    public VerifyExecutedMoveObject verifyExecutedMove(int gameID, String pieceID, int[] previousPiecePosition, int[] newPiecePosition){
        GameController gc = GameController.getInstance();

        boolean isMoveValid = gc.verifyExecutedMove(gameID, pieceID, previousPiecePosition, newPiecePosition);
        Object[][] chessboard = gc.getChessboard(gameID);

        return new VerifyExecutedMoveObject(gameID, isMoveValid, chessboard);
    }
}
