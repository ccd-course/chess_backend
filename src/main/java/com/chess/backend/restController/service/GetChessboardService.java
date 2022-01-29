package com.chess.backend.restController.service;

import com.chess.backend.gamemodel.Square;
import com.chess.backend.restController.objects.ChessboardObject;
import com.chess.backend.restController.objects.SquareObject;
import com.chess.backend.services.ChessGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * This class handles the call to get the chessboard.
 *
 * @author Hannes Stuetzer
 */
@Service
public class GetChessboardService {
    private final ChessGameService gameService;

    @Autowired
    public GetChessboardService(ChessGameService gameService ){
        this.gameService = gameService;
    }

    /**
     * This methods calls the {@link ChessGameService} to get the chessboard.
     *
     * @param gameID the id of the current game.
     * @return containing the chessboard.
     */
    public ChessboardObject getChessboard(int gameID) {
        ArrayList<ArrayList<Square>> chessboard = this.gameService.getBoard(gameID);

        //[4][24]
        /*
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
         */

        //[24][4]
        SquareObject[][] board = new SquareObject[chessboard.get(0).size()][chessboard.size()];

        for(int i = 0; i < chessboard.size(); i++){
            for(int j = 0; j < chessboard.get(i).size(); j++){
                if(chessboard.get(i).get(j).hasPiece()){
                    board[j][i] = new SquareObject(chessboard.get(i).get(j).getPiece().getType().getLabel(), chessboard.get(i).get(j).getPiece().getPlayer().getName());
                } else {
                    board[j][i] = null;
                }
            }
        }

        return new ChessboardObject(board);
    }
}
