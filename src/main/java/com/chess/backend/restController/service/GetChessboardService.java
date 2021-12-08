package com.chess.backend.restController.service;

import com.chess.backend.gamemodel.Square;
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
        Square[][] chessboard = gc.getChessboard(gameID);

        SquareObject[][] board = new SquareObject[chessboard.length][chessboard[0].length];

        for(int i = 0; i < chessboard.length; i++){
            for(int j = 0; j < chessboard[i].length; j++){
                if(chessboard[i][j].hasPiece()){
                    board[i][j] = new SquareObject(chessboard[i][j].getPiece().getType().getLabel(), chessboard[i][j].getPiece().getPlayer().getName());
                } else {
                    board[i][j] = null;
                }
            }
        }

        return new ChessboardObject(board);
    }
}
