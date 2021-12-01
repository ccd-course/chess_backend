package com.chess.backend.restController.service;

import com.chess.backend.GameController;
import com.chess.backend.restController.objects.MoveRequestObject;
import org.springframework.stereotype.Service;

@Service
public class MoveRequestService {
    public MoveRequestObject getPossibleMoves(int gameID, String pieceID, int[] piecePosition){

        GameController gc = GameController.getInstance();

        return new MoveRequestObject(gameID, pieceID, piecePosition, gc.getPossibleMoves(gameID, pieceID, piecePosition));
    }
}
