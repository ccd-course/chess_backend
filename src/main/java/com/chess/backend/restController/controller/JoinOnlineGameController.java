//package com.chess.backend.restController.controller;
//
//
//import com.chess.backend.restController.objects.NewOnlineGameObject;
//import com.chess.backend.restController.service.NewOnlineGameService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * This class handles the API-call to create a new game.
// *
// * @author Hannes Stuetzer
// */
//@RestController
//@CrossOrigin
//@RequestMapping("/createOnlineNewGame")
//public class JoinOnlineGameController {
//
//    private final NewOnlineGameService newGameService;
//
//    @Autowired
//    public JoinOnlineGameController(NewOnlineGameService newGameService) {
//        this.newGameService = newGameService;
//    }
//
//    /**
//     * Method that is called on a post request.
//     *
//     */
//    @Operation(
//            summary = "Start a new online game",
//            description = "Returns the Game ID of a newly created game."
//    )
//    @PostMapping
//    public int joinOnlineGameController(@RequestParam(value = "gameID") int gameID) {
//          return 1;
////        return newGameService.getNewGameID(newGameObject);
//    }
//}