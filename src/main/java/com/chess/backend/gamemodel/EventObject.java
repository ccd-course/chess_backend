package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.constants.Event;
import lombok.Data;


@Data
public class EventObject {
    Event type;
    EventMetadata metadata;
    public EventObject(){
    }
    public EventObject(Event type){
        this.type = type;
    }
    public EventObject(Event type, EventMetadata metadata){
        this.type = type;
        this.metadata = metadata;
    }
}
