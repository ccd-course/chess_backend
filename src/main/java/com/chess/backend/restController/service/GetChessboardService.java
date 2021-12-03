package com.chess.backend.restController.service;

import com.chess.backend.services.GameService;
import com.chess.backend.restController.objects.ChessboardObject;
import org.springframework.stereotype.Service;

@Service
public class GetChessboardService {
    public ChessboardObject getChessboard(int gameID){
        GameService gc = GameService.getInstance();

        return new ChessboardObject(gameID, gc.getChessboard(gameID));
    }
}
