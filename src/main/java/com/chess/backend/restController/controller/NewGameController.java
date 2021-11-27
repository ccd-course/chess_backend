package com.chess.backend.restController.controller;

import com.chess.backend.restController.objects.NewGameObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newGame")
public class NewGameController {

    @GetMapping
    public NewGameObject newGame(@RequestParam(value = "playerNumber") int playerNumber){
        //TODO: call of createNewGame method with parameter playerNumber
        //returns the gameID and the chessboard

        //just testing
        //http://localhost:8080/newGame?playerNumber=3
        Object[][] chessboard = new Object[2][3];
        chessboard[0][0] = "Test";
        int gameID = 1;

        return new NewGameObject(gameID, chessboard);
    }
}
