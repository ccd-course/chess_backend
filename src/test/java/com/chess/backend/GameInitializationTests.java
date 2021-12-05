package com.chess.backend;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.services.ChessboardService;
import com.chess.backend.services.GameService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameInitializationTests {

    @Test
    void testGameInitialization() {
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        GameService gameService = new GameService();
        gameService.createNewGame(players);
        Game game = gameService.getGame();
        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
                game.chessboard.squares, PieceType.KING, null, null);

        assertTrue(squares.size() == game.getChessboard().numberOfPlayers);
        System.out.println(squares.size());
    }

}
