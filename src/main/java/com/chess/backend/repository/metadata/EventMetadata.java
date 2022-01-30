package com.chess.backend.repository.metadata;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventMetadata{
    int[] start;
    int[] end;
    int playerId;
    String playerName;
    public EventMetadata(int [] start, int[] end, int playerId, String playerName){
        this.start = start;
        this.end = end;
        this.playerId = playerId;
        this.playerName = playerName;
    }
    public EventMetadata(int playerId, String playerName){
        this.playerId = playerId;
        this.playerName = playerName;
    }


}
