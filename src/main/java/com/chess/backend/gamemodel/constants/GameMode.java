package com.chess.backend.gamemodel.constants;

public enum GameMode {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE");
    private final String value;

    GameMode(final String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }
}
