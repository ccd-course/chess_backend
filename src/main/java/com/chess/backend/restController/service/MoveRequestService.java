package com.chess.backend.restController.service;

import com.chess.backend.services.GameService;
import com.chess.backend.restController.objects.MoveRequestObject;
import org.springframework.stereotype.Service;

@Service
public class MoveRequestService {

    public MoveRequestObject getPossibleMoves(int gameID, String pieceID, int[] piecePosition){

        GameService gc = GameService.getInstance();

        return new MoveRequestObject(gameID, pieceID, piecePosition, gc.getPossibleMoves(gameID, pieceID, piecePosition));
    }
}
