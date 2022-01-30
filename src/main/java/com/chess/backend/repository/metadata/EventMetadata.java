package com.chess.backend.repository.metadata;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventMetadata{
    int[] start;
    int[] end;
    int playerId;
    String playerName;
    boolean fixed;

    public EventMetadata(int [] start, int[] end, int playerId, String playerName, boolean fixed){
        this.start = start;
        this.end = end;
        this.playerId = playerId;
        this.playerName = playerName;
        this.fixed = fixed;
    }
    public EventMetadata(int [] start, int[] end, int playerId, String playerName){
        this(start, end, playerId, playerName, false);
    }
    public EventMetadata(int playerId, String playerName){
        this.playerId = playerId;
        this.playerName = playerName;
    }


}
