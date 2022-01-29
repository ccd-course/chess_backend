package com.chess.backend.repository;

import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.restController.objects.SquareObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
@Qualifier("GameRepositoryClass")
public class GameRepository implements IGameRepository {

    @Autowired
    private Firebase firebase;

    private static final String collection = "game";

    public ChessGame getGame(Integer gameId){
        Map<String, Object> gameHashMap = this.firebase.getDocument(collection, String.valueOf(gameId));
        String gameJson = (String) gameHashMap.get("value");
        return new Gson().fromJson(gameJson, ChessGame.class);
    }

    /**
     * Transforms the chessboard to a simplified version and switches the axis.
     * @param chessboard The chessboard.
     * @return The transformed and simplified chessboard.
     */
    private SquareObject[][] transformChessboard(Chessboard chessboard){
        SquareObject[][] board = new SquareObject[chessboard.getSquares().get(0).size()][chessboard.getSquares().size()];

        for(int i = 0; i < chessboard.getSquares().size(); i++){
            for(int j = 0; j < chessboard.getSquares().get(i).size(); j++){
                if(chessboard.getSquares().get(i).get(j).hasPiece()){
                    board[j][i] = new SquareObject(chessboard.getSquares().get(i).get(j).getPiece().getType().getLabel(), chessboard.getSquares().get(i).get(j).getPiece().getPlayer().getId());
                } else {
                    board[j][i] = null;
                }
            }
        }

        return board;
    }

    public void createNewGame(Integer gameId, ChessGame game)  {

        String gameJson = new Gson().toJson(game);
        Map<String, Object> data = new HashMap<>();
        data.put("value", gameJson);
        data.put("events", game.getEvents());
        data.put("type", game.getType());
        String chessboardJson = new Gson().toJson(transformChessboard(game.getChessboard()));
        data.put("chessboard", chessboardJson);
        this.firebase.addDocument(collection, String.valueOf(gameId),data);

    }
}
