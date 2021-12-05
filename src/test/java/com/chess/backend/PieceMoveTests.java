package com.chess.backend;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.services.ChessboardService;
import com.chess.backend.services.GameService;
import org.junit.jupiter.api.Test;

public class PieceMoveTests {

    @Test
    void testKingMovement() {
        Player player1 = new Player("Test Player 1", Color.WHITE, 1);
        Player player2 = new Player("Test Player 2", Color.BLACK, 2);
        Player player3 = new Player("Test Player 3", Color.RED, 3);

        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        GameService gameService = new GameService();
        gameService.createNewGame(players);
        int gameID = gameService.getGameID();

        Game game = gameService.getGame();
        ChessboardService.searchSquaresByPiece(game.chessboard.squares, PieceType.KING, Color.WHITE, game.)
    }

}
