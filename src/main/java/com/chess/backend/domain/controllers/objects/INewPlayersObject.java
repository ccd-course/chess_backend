package com.chess.backend.domain.controllers.objects;

public interface INewPlayersObject {
    String[] getAllPlayerNames();

    INewPlayerObject[] getPlayers();

    void setPlayers(INewPlayerObject[] players);
}
