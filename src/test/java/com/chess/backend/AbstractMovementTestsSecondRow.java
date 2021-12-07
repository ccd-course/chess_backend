package com.chess.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.services.ChessboardService;
import com.chess.backend.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class AbstractMovementTestsSecondRow {

    Game game;

    @BeforeEach
    void setUp() {
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        GameService gameService = new GameService();
        gameService.createNewGame(players);
        game = gameService.getGame();
    }

    @Test
    void testPawn() {
        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
                game.chessboard.squares, PieceType.PAWN, Color.WHITE, null);

        Square selectedSquare = squares.get(0);
        Piece selectedPiece = selectedSquare.getPiece();
        HashSet<Move> possibleMoves = selectedPiece.getAllowedFullMoves(game);
        Iterator<Move> selectedMoveIterator = possibleMoves.iterator();
        Move selectedMove = selectedMoveIterator.next();

        // One Forward
        assertEquals(selectedMove.getToPos(), selectedSquare.getPos().forward(game.getChessboard()));
        // Two Forward
        assertEquals(selectedMoveIterator.next().getToPos(), selectedSquare.getPos().forward(game.getChessboard()).forward(game.chessboard));

        //assertTrue(possibleMoves.iterator().next());
        System.out.println(possibleMoves);
    }

}