package com.chess.backend.gamemodel;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventMetadata{
    int[] start;
    int[] end;
    int playerId;
    public EventMetadata(int [] start, int[] end){
        this.start = start;
        this.end = end;
    }
    public EventMetadata(int playerId){
        this.playerId = playerId;
    }
}
