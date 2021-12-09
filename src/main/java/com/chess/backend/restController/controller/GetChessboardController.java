package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.ChessboardObject;
import com.chess.backend.restController.service.GetChessboardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles the API-call to get the current chessboard.
 *
 * @author Hannes Stuetzer
 */
@RestController
@CrossOrigin
@RequestMapping("/getChessboard")
public class GetChessboardController {
    private final GetChessboardService getChessboardService;

    @Autowired
    public GetChessboardController(GetChessboardService getChessboardService) {
        this.getChessboardService = getChessboardService;
    }

    /**
     * Method that is called on a get request.
     *
     * @param gameID the id of the current game (as params).
     * @return a {@link ChessboardObject} containing the current chessboard.
     */
    @Operation(
            summary = "Get the current chessboard",
            description = "Returns the current chessboard with all piece positions."
    )
    @GetMapping
    public ChessboardObject getChessboard(@RequestParam(value = "gameID") int gameID) {
        return getChessboardService.getChessboard(gameID);
    }
}
