package com.chess.backend.gamemodel.constants;

public enum Event {
    NEW_PLAYER_JOIN("NEW_PLAYER_JOIN"),
    GAME_STARTED("GAME_STARTED"),
    NEW_MOVE("NEW_MOVE"),
    CHECKMATED("CHECKMATED"),
    DRAW("DRAW");


    private final String label;

    Event(String label) {
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

}
