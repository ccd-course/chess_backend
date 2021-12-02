package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.GetPlayerTurnObject;
import com.chess.backend.restController.service.GetPlayerTurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getPlayerTurn")
public class GetPlayerTurnController {
    private final GetPlayerTurnService playerTurnService;

    @Autowired
    public GetPlayerTurnController(GetPlayerTurnService playerTurnService){
        this.playerTurnService = playerTurnService;
    }

    @GetMapping
    public GetPlayerTurnObject getPlayerTurn(@RequestParam(value = "gameID") int gameID){
        return playerTurnService.getPlayerTurn(gameID);
    }
}
