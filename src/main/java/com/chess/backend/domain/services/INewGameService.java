package com.chess.backend.domain.services;


import com.chess.backend.domain.controllers.objects.INewPlayersObject;

public interface INewGameService {
    int getNewGameID(INewPlayersObject players);
}
