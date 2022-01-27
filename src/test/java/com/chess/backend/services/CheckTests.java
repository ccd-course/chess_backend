package com.chess.backend.services;

import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckTests {

    ChessGame game;
    King wKing;
    Queen bQueen;
    Queen rQueen;

    @BeforeEach
    void setUp(){
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        ChessGameService chessGameService = new ChessGameService();
        chessGameService.createNewGame(players);
        game = chessGameService.getGame();

        wKing = new King(game.getPlayers().get(0), true);
        bQueen = new Queen(game.getPlayers().get(1), true);
        rQueen = new Queen(game.getPlayers().get(2), true);
    }

    void setUpValidMoveBoard(IPiece piece){
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(2, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), piece);
    }

    void setUpNoValidMoveBoard(IPiece piece){
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(1, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), piece);
    }

    @Test
    void testHasPlayerValidMoves(){
        // test with initial chessboard
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getActivePlayer()));

        Queen wQueen = new Queen(game.getPlayers().get(0), true);
        Rook wRook = new Rook(game.getPlayers().get(0), true);
        Bishop wBishop = new Bishop(game.getPlayers().get(0), true);
        Knight wKnight = new Knight(game.getPlayers().get(0), true);
        Pawn wPawn = new Pawn(game.getPlayers().get(0), true);

        // test King valid move
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), wKing);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Queen valid move
        setUpValidMoveBoard(wQueen);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Rook valid move
        setUpValidMoveBoard(wRook);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Bishop valid move
        setUpValidMoveBoard(wBishop);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Knight valid move
        setUpValidMoveBoard(wKnight);
        assertTrue(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Pawn valid move
        setUpValidMoveBoard(wPawn);
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
        setUpNoValidMoveBoard(wQueen);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Rook no valid move
        setUpNoValidMoveBoard(wRook);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Bishop no valid move
        setUpNoValidMoveBoard(wBishop);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Knight no valid move
        setUpNoValidMoveBoard(wKnight);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));

        // test Pawn no valid move
        setUpNoValidMoveBoard(wPawn);
        assertFalse(ChessboardService.hasPlayerValidMoves(game.getChessboard(), game.getPlayers().get(0)));
    }
    @Test
    void testGetCaptureKingPlayers(){
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        ChessGameService chessGameService = new ChessGameService();
        chessGameService.createNewGame(players);
        ChessGame game = chessGameService.getGame();

        King bKing = new King(game.getPlayers().get(1), true);
        King rKing = new King(game.getPlayers().get(2), true);

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
