package com.chess.backend.services;

import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.pieces.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class ChessboardServiceTests {
    @Test
    void testNumberOfPlayers() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(Arrays.asList(players));
        assertEquals(chessboard.getNumberOfPlayers(), 3);

    }
    @Test
    void testChessboardSize() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(Arrays.asList(players));
        assertEquals(chessboard.getSquares().length, 5);
        assertEquals(chessboard.getSquares()[0].length, 27);

    }
    @Test
    void testHasPlayerValidMoves(){

        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        ChessGameService chessGameService = new ChessGameService();
        chessGameService.createNewGame(players);
        ChessGame game = chessGameService.getGame();

        // test with initial chessboard
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getActivePlayer()));

        King wKing = new King(game.getPlayers().get(0), true);
        Queen wQueen = new Queen(game.getPlayers().get(0), true);
        Rook wRook = new Rook(game.getPlayers().get(0), true);
        Bishop wBishop = new Bishop(game.getPlayers().get(0), true);
        Knight wKnight = new Knight(game.getPlayers().get(0), true);
        Pawn wPawn = new Pawn(game.getPlayers().get(0), true);
        Queen bQueen = new Queen(game.getPlayers().get(1), true);
        Queen rQueen = new Queen(game.getPlayers().get(2), true);

        // test King valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wKing);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Queen valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(2, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wQueen);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Rook valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(2, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wRook);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Bishop valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(2, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wBishop);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Knight valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(2, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wKnight);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Pawn valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(2, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wPawn);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        //test no valid move
        /*
            # Q #
            K # #
            # Q #
         */
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(1, 2, game.getChessboard().getSquares(), rQueen);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Queen no valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(1, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wQueen);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Rook no valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(1, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wRook);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Bishop no valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(1, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wBishop);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Knight no valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(1, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wKnight);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Pawn no valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(1, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wPawn);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));
    }
    @Test
    void testGetCaptureKingPlayers(){
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        ChessGameService chessGameService = new ChessGameService();
        chessGameService.createNewGame(players);
        ChessGame game = chessGameService.getGame();

        King wKing = new King(game.getPlayers().get(0), true);
        King bKing = new King(game.getPlayers().get(1), true);
        King rKing = new King(game.getPlayers().get(2), true);
        Queen bQueen = new Queen(game.getPlayers().get(1), true);
        Queen rQueen = new Queen(game.getPlayers().get(2), true);

        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(3, 10, game.getChessboard().getSquares(), bKing);
        ChessboardService.setPiece(4, 15, game.getChessboard().getSquares(), rKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);

        assertEquals(ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getPlayers().get(1)).size(), 1);
        assertTrue(ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getPlayers().get(1)).get(0).getName() == players[0]);

        assertEquals(ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getPlayers().get(2)).size(), 0);
    }
}
