package com.chess.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document()
public class GameHistory {
    @Id
    private String id;
    private String gameId;
    private String created;
    private String playerID;
    private Square[][] chessboard;

}
