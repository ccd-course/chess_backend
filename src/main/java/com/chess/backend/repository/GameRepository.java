package com.chess.backend.repository;

import com.chess.backend.domain.models.IPiece;
import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.gamemodel.pieces.*;
import com.chess.backend.restController.objects.ChessboardObject;
import com.chess.backend.restController.objects.SquareObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
@Qualifier("GameRepositoryClass")
public class GameRepository implements IGameRepository {
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
        try{
            HashMap result =
                    new ObjectMapper().readValue(gameJson, HashMap.class);
            HashMap chessboard = (HashMap) result.get("chessboard");
            ArrayList<ArrayList<LinkedHashMap>> squares = (ArrayList<ArrayList<LinkedHashMap>>) chessboard.get("squares");
            ChessGame newGame=  new Gson().fromJson(gameJson, ChessGame.class);
            System.out.println("GAMME:: " + newGame);
            for(int i =0; i< squares.size(); i++){
                for( int j =0; j < squares.get(i).size(); j++){
                    System.out.println("SQUARE::" + squares.get(i).get(j));
                    LinkedHashMap square = squares.get(i).get(j);
                    LinkedHashMap piece = (LinkedHashMap) square.get("piece");

                    if (piece != null) {
                        String pieceJson = new Gson().toJson(piece);
                        Class pieceClass = null;
                        if (piece.get("type").toString().equals(PieceType.PAWN.getLabel().toUpperCase())) {
                            pieceClass = Pawn.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.ROOK.getLabel().toUpperCase())) {
                            pieceClass = Rook.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.KNIGHT.getLabel().toUpperCase())) {
                            pieceClass = Knight.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.BISHOP.getLabel().toUpperCase())) {
                            pieceClass = Bishop.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.QUEEN.getLabel().toUpperCase())) {
                            pieceClass = Queen.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.KING.getLabel().toUpperCase())) {
                            pieceClass = King.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.CANNON.getLabel().toUpperCase())) {
                            pieceClass = Cannon.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.FERZ.getLabel().toUpperCase())) {
                            pieceClass = Ferz.class;
                        }
                        else if (piece.get("type").toString().equals(PieceType.WAZIR.getLabel().toUpperCase())) {
                            pieceClass = Wazir.class;
                        }
                        Piece pawn=  new Gson().fromJson(pieceJson, (Type) pieceClass);
                        System.out.println();
                        newGame.getChessboard().getSquares().get(i).get(j).setPiece(pawn);
                    }
                }
            }
            System.out.println("newGame:" + newGame);
            return newGame;
        }
        catch (JsonProcessingException e){
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());

        }
        return null;
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
