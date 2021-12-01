package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.ChessboardObject;
import com.chess.backend.restController.service.GetChessboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getChessboard")
public class GetChessboardController {
    private final GetChessboardService getChessboardService;

    @Autowired
    public GetChessboardController(GetChessboardService getChessboardService) {
        this.getChessboardService = getChessboardService;
    }

    @GetMapping
    public ChessboardObject getChessboard(@RequestParam(value = "gameID") int gameID){
        return getChessboardService.getChessboard(gameID);
    }
}
