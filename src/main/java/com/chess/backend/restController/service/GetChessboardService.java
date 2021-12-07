package com.chess.backend.restController.service;

import com.chess.backend.restController.objects.SquareObject;
import com.chess.backend.services.GameService;
import com.chess.backend.restController.objects.ChessboardObject;
import org.springframework.stereotype.Service;

/**
 * This class handles the call to get the chessboard.
 *
 * @author Hannes Stuetzer
 */
@Service
public class GetChessboardService {
    /**
     * This methods calls the {@link GameService} to get the chessboard.
     *
     * @param gameID the id of the current game.
     * @return containing the chessboard.
     */
    public ChessboardObject getChessboard(int gameID){
        GameService gc = GameService.getInstance();

        gc.getChessboard(gameID);

        return new ChessboardObject(new SquareObject[][]{});
    }
}
