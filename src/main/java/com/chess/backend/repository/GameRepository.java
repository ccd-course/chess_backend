package com.chess.backend.repository;

import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.gamemodel.pieces.Pawn;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.restController.objects.ChessboardObject;
import com.chess.backend.restController.objects.SquareObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class GameRepository {
    private final Firebase firebase = new Firebase();
    private static final String collection = "game";

    public ChessGame getGame(Integer gameId){

//        String gameJson = new Gson().toJson(game);
//        System.out.println(gameJson);
//        Map<String, Object> data = new HashMap<>();
//        data.put("value", gameJson);
//        ChessGame newGame=  new Gson().fromJson(gameJson, ChessGame.class);
//        System.out.println("newGame:" + newGame);

        Map<String, Object> gameHashMap = this.firebase.getDocument(collection, String.valueOf(gameId));
        String gameJson = (String) gameHashMap.get("value");
        ChessGame newGame=  new Gson().fromJson(gameJson, ChessGame.class);
        ArrayList<ArrayList<Square>> squares = newGame.getChessboard().getSquares();
        for (ArrayList<Square> squareArrayList : squares) {
            for (Square square : squareArrayList) {
                Piece piece = square.getPiece();
                String pieceJson = new Gson().toJson(piece);
                if (piece != null) {
                    if (piece.getType() == PieceType.PAWN) {
                        //new Pawn()
                        Pawn pawn=  new Gson().fromJson(gameJson, Pawn.class);
                        piece = pawn;
                        System.out.println("TEST pawn Type " + pawn.getType());
//                        new Pawn(square.getPiece().getPlayer(), piece.isClockwise());
                    }
                    square.setPiece(piece);
                }
            }
        }

        System.out.println("newGame:" + newGame);
        return newGame;

    }

    public void createNewGame(Integer gameId, ChessGame game)  {

        String gameJson = new Gson().toJson(game);
        System.out.println("JSON:" +gameJson);
        Map<String, Object> data = new HashMap<>();
        data.put("value", gameJson);

//        try{
//            HashMap<String,Object> result =
//                    new ObjectMapper().readValue(gameJson, HashMap.class);
//            System.out.println(result);
//        }
//        catch (JsonProcessingException e){
//            System.out.println(e.getMessage());
//            System.out.println(e.getStackTrace());
//
//        }

//        ChessGame newGame=  new Gson().fromJson(gameJson, ChessGame.class);
//        System.out.println("newGame:" + newGame);
//        try{
//            HashMap result = new ObjectMapper().readValue(gameJson, HashMap.class);
//            System.out.println("RESULT "+ result);
        this.firebase.addDocument(collection, String.valueOf(gameId),data);

//        }
//        catch (JsonProcessingException e){
//            System.out.println(e.getMessage());
//        }
    }
}
