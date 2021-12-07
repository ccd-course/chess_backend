package com.chess.backend.restController.service;

import com.chess.backend.restController.objects.MoveRequestInputObject;
import com.chess.backend.restController.objects.MoveRequestOutputObject;
import com.chess.backend.services.GameService;
import org.springframework.stereotype.Service;

@Service
public class MoveRequestService {

    public MoveRequestOutputObject getPossibleMoves(MoveRequestInputObject moveRequestInputObject){

        GameService gc = GameService.getInstance();

        return new MoveRequestOutputObject(gc.getPossibleMoves(moveRequestInputObject.getGameID(), moveRequestInputObject.getPiecePosition()));
    }
}
