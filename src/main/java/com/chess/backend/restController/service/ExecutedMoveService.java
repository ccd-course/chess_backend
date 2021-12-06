package com.chess.backend.restController.service;

import com.chess.backend.services.GameService;
import com.chess.backend.restController.objects.ExecutedMoveObject;
import org.springframework.stereotype.Service;

@Service
public class ExecutedMoveService {
    public boolean executedMove(ExecutedMoveObject executedMoveObject){
        GameService gc = GameService.getInstance();

        return gc.executedMove(executedMoveObject.getGameID(), executedMoveObject.getPreviousPiecePosition(), executedMoveObject.getNewPiecePosition());
    }
}
