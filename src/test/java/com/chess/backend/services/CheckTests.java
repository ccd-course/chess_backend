package com.chess.backend.services;

import com.chess.backend.ChessGameInitializationTests;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.constants.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckTests {

    ChessGame game;
    Piece wKing;
    Piece bQueen;
    Piece rQueen;

    @BeforeEach
    void setUp(){
        game = ChessGameInitializationTests.createNewTestGame();

        wKing = new Piece(PieceType.KING, game.getPlayers().get(0), true);
        bQueen = new Piece(PieceType.QUEEN, game.getPlayers().get(1), true);
        rQueen = new Piece(PieceType.QUEEN, game.getPlayers().get(2), true);
    }

    void setUpValidMoveBoard(Piece piece){
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(2, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);
        ChessboardService.setPiece(2, 10, game.getChessboard().getSquares(), piece);
    }

    void setUpNoValidMoveBoard(Piece piece){
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

        Piece wQueen = new Piece(PieceType.QUEEN, game.getPlayers().get(0), true);
        Piece wRook = new Piece(PieceType.ROOK, game.getPlayers().get(0), true);
        Piece wBishop = new Piece(PieceType.BISHOP, game.getPlayers().get(0), true);
        Piece wKnight = new Piece(PieceType.KNIGHT, game.getPlayers().get(0), true);
        Piece wPawn = new Piece(PieceType.PAWN, game.getPlayers().get(0), true);

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
        Piece bKing = new Piece(PieceType.KING, game.getPlayers().get(1), true);
        Piece rKing = new Piece(PieceType.KING, game.getPlayers().get(2), true);

        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(0, 1, game.getChessboard().getSquares(), wKing);
        ChessboardService.setPiece(3, 10, game.getChessboard().getSquares(), bKing);
        ChessboardService.setPiece(4, 15, game.getChessboard().getSquares(), rKing);
        ChessboardService.setPiece(1, 0, game.getChessboard().getSquares(), bQueen);
        ChessboardService.setPiece(2, 2, game.getChessboard().getSquares(), rQueen);

        assertEquals(1, ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getPlayers().get(1)).size());
        assertSame("Test Player 1", ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getPlayers().get(1)).get(0).getName());

        assertEquals(0, ChessboardService.getCaptureKingPlayers(game.getChessboard(), game.getPlayers().get(2)).size());
    }
}
