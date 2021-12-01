package com.chess.backend.restController.service;

import com.chess.backend.GameController;
import com.chess.backend.restController.objects.ChessboardObject;
import org.springframework.stereotype.Service;

@Service
public class GetChessboardService {
    public ChessboardObject getChessboard(int gameID){
        GameController gc = GameController.getInstance();

        return new ChessboardObject(gameID, gc.getChessboard(gameID));
    }
}
