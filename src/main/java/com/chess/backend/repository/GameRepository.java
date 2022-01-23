package com.chess.backend.repository;

import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.restController.objects.ChessboardObject;
import com.chess.backend.restController.objects.SquareObject;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameRepository {
    private final Firebase firebase = new Firebase();
    private static final String collection = "game";

    public void createNewGame(Integer gameId, ChessGame game){

        String gameJson = new Gson().toJson(game);
        System.out.println(gameJson);
        Map<String, Object> data = new HashMap<>();
        data.put("value", gameJson);
//        ChessGame newGame=  new Gson().fromJson(gameJson, ChessGame.class);
//        System.out.println("newGame:" + newGame);

        this.firebase.addDocument(collection, String.valueOf(gameId),data);
    }
}
