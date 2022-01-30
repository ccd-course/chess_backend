package com.chess.backend.gamemodel.constants;

/**
 * Represents different event types for the firebase database.
 */
public enum Event {
    NEW_PLAYER_JOIN("NEW_PLAYER_JOIN"),
    GAME_STARTED("GAME_STARTED"),
    NEW_MOVE("NEW_MOVE"),
    PLAYER_CHANGE("PLAYER_CHANGE"),
    CHECKMATED("CHECKMATED"),
    DRAW("DRAW"),
    PROMOTE("PROMOTE");


    private final String label;

    Event(String label) {
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

}
