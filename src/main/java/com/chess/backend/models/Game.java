package com.chess.backend.models;
import com.chess.backend.constants.COLOR;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class Player{
    private String id;
    private COLOR color;
}
@Data
class Square{
    private String playerId;
    private String pieceId;
    private String pieceType;

}

@Data
@Document()
public class Game {
    @Id
    private String id;
    private List<Player> players;
    private Square[][] chessboard;

}
