package com.chess.backend.domain.services;


import com.chess.backend.domain.controllers.objects.INewPlayersObject;
import com.chess.backend.restController.objects.NewGameObject;

public interface INewGameService {
    int getNewGameID(NewGameObject gameObject);
}
