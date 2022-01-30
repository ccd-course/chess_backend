package com.chess.backend.services;

import com.chess.backend.ChessGameInitializationTests;
import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.repository.GameRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChessGameServiceTests {

    ChessGame game;
    IGameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepositoryMock();
        game = ChessGameInitializationTests.createNewTestGame(gameRepository);
    }

    @Test
    void testActivePlayerSwitchCannon(){

        assertEquals(6,
                ChessboardService.searchSquaresByPiece(
                game.getChessboard().getSquares(),
                PieceType.CANNON,
                null,
                game.getActivePlayer()
                ).size());

        assertEquals("Test Player 1",
                game.getActivePlayer().getName());

        ChessGameService.switchActive(game);

        assertEquals(6,
                ChessboardService.searchSquaresByPiece(
                game.getChessboard().getSquares(),
                PieceType.CANNON,
                null,
                game.getActivePlayer()
                ).size());

        assertEquals("Test Player 2",
                game.getActivePlayer().getName());

    }

    @Test
    void getNewGameID() {
        assertTrue(ChessGameService.getNewGameID() > 0);
    }

    @Test
    void createNewGame() {
        String[] playerNames = {"Player 1", null};
        ChessGame testGame = ChessGameService.createNewGame(playerNames);
        assertTrue(testGame.getId() != 0);
    }

    @Test
    void getGame() {
        ChessGameService chessGameService = new ChessGameService(gameRepository);
        ChessGame testGame = chessGameService.getGame(game.getId());
        assertNotNull(testGame);
    }

    @Test
    void getBoard() {
        ChessGameService chessGameService = new ChessGameService(gameRepository);
        ArrayList<ArrayList<Square>> chessBoard = chessGameService.getBoard(game.getId());
        assertNotNull(chessBoard);
    }

    @Test
    void getAllSquaresFromChessboard() {
        ArrayList<ArrayList<Square>> chessBoard = ChessGameService.getAllSquaresFromChessboard(game);
        assertNotNull(chessBoard);
    }

    @Test
    void validateMove() {

    }

    @Test
    void getPossibleMoves() {
    }

    @Test
    void executedMove() {
    }

    @Test
    void getActivePlayerName() {
        assertEquals("Test Player 1", ChessGameService.getActivePlayerName(game));
    }

    @Test
    void switchActive() {
        Player oldActivePlayer = game.getActivePlayer();
        ChessGameService.switchActive(game);
        assertNotEquals(game.getActivePlayer(), oldActivePlayer);
    }

    @Test
    void getPlayerTurn() {
        ChessGameService chessGameService = new ChessGameService(gameRepository);
        assertEquals("Test Player 1", chessGameService.getPlayerTurn(game.getId()));
    }

    @Test
    void setActivePlayer() {
        game.setActivePlayer(game.getPlayers().get(1));
        assertEquals("Test Player 2", game.getActivePlayer().getName());
    }
}
