package com.chess.backend.repository;

import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.restController.objects.ChessboardObject;
import com.chess.backend.restController.objects.SquareObject;

import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameRepository {
    private final Firebase firebase = new Firebase();
    private static final String collection = "game";

    public void createNewGame(Integer gameId, ChessGame game){
//        Square[][] chessboard = game.getChessboard().getSquares();
//        SquareObject[][] board = new SquareObject[chessboard[0].length][chessboard.length];
//
//        for(int i = 0; i < chessboard.length; i++){
//            for(int j = 0; j < chessboard[i].length; j++){
//                if(chessboard[i][j].hasPiece()){
//                    board[j][i] = new SquareObject(chessboard[i][j].getPiece().getType().getLabel(), chessboard[i][j].getPiece().getPlayer().getName());
//                } else {
//                    board[j][i] = null;
//                }
//            }
//        }

//        ChessboardObject chessboardSimpleObject = new ChessboardObject(board);
//        String chessBoardJson = new Gson().toJson(chessboardSimpleObject.getChessboard());
//        Map<String, Object> data = new HashMap<>();
//        data.put("chessboard", chessBoardJson);
        this.firebase.addDocument(collection, String.valueOf(gameId),game);
    }
}
