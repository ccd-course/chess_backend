package com.chess.backend.restController.service;

import com.chess.backend.GameController;
import com.chess.backend.restController.objects.GetChessboardObject;
import org.springframework.stereotype.Service;

@Service
public class GetChessboardService {
    public GetChessboardObject getChessboard(int gameID){
        GameController gc = GameController.getInstance();

        return new GetChessboardObject(gameID, gc.getChessboard(gameID));
    }
}
