package com.chess.backend.domain.controllers.objects;

import com.chess.backend.restController.objects.NewPlayerObject;

public interface INewPlayersObject {
    String[] getAllPlayerNames();

    NewPlayerObject[] getPlayers();

    void setPlayers(NewPlayerObject[] players);
}
