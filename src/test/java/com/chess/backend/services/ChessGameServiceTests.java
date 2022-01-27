package com.chess.backend.services;

import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.constants.PieceType;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChessGameServiceTests {

    ChessGame game;


    @BeforeEach
    void setUp(){
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        ChessGameService chessGameService = new ChessGameService();
        chessGameService.createNewGame(players);
        game = chessGameService.getGame();
    }

    @Test
    void testActivePlayerSwitchCannon(){

        assertEquals(3,
                ChessboardService.searchSquaresByPiece(
                game.getChessboard().getSquares(),
                PieceType.CANNON,
                null,
                game.getActivePlayer()
                ).size());

        assertEquals("Test Player 1",
                game.getActivePlayer().getName());

        ChessGameService.switchActive(game);

        assertEquals(3,
                ChessboardService.searchSquaresByPiece(
                game.getChessboard().getSquares(),
                PieceType.CANNON,
                null,
                game.getActivePlayer()
                ).size());

        assertEquals("Test Player 2",
                game.getActivePlayer().getName());

    }

}
