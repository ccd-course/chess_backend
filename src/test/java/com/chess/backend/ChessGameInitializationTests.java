package com.chess.backend;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.chess.backend.repository.GameRepositoryMock;
import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.restController.objects.NewGameObject;
import com.chess.backend.restController.objects.NewPlayerObject;
import com.chess.backend.restController.objects.NewPlayersObject;
import com.chess.backend.restController.service.NewChessGameService;
import com.chess.backend.services.ChessboardService;
import com.chess.backend.services.ChessGameService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ChessGameInitializationTests {

    /**
     * Helper method to create a ChessGame test object
     * @return ChessGame
     */
    public static ChessGame createNewTestGame(IGameRepository gameRepository) {
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        NewPlayerObject[] newPlayerObjects = new NewPlayerObject[players.length];
        for (int i = 0; i < players.length; i++) {
            NewPlayerObject newPlayerObject = new NewPlayerObject();
            newPlayerObject.setPlayerName(players[i]);
            newPlayerObjects[i] = newPlayerObject;
        }
        NewGameObject gameObject = new NewGameObject();
        ChessGameService chessGameService = new ChessGameService(gameRepository);
        NewChessGameService newChessGameService = new NewChessGameService(chessGameService, gameRepository);
        gameObject.setPlayers(newPlayerObjects);
        int gameID = newChessGameService.getNewGameID(gameObject);
        return chessGameService.getGame(gameID);
    }

    /**
     * Helper method to create a ChessGame test object
     * @return ChessGame
     */
    public static ChessGame createNewTestGame() {
        IGameRepository gameRepository = new GameRepositoryMock();
        return createNewTestGame(gameRepository);
    }

    /**
     * Test the game initialization.
     */
    @Test
    void testGameInitialization() {
        ChessGame game = createNewTestGame();

        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
                game.getChessboard().getSquares(), PieceType.KING, null, null);

        assertEquals(squares.size(), game.getChessboard().getNumberOfPlayers());
        System.out.println(squares.size());
    }

}
