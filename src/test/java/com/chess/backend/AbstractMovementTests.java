package com.chess.backend;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.services.ChessboardService;
import com.chess.backend.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractMovementTests {

    Game game;

    @BeforeEach
    void setUp() {
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        GameService gameService = new GameService();
        gameService.createNewGame(players);
        game = gameService.getGame();
    }


    void removeOtherPieceTypes(PieceType pieceType) {
        ArrayList<Square> squares = ChessboardService.getOccupiedSquares(game.getChessboard().getSquares());
        for (Square square :
                squares) {
            if (square.getPiece().getType() != pieceType) {
                square.removePiece();
            }
        }
    }

    HashSet<Position> getPossibleMovePositions(PieceType pieceType) {
        removeOtherPieceTypes(pieceType);
        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
                game.chessboard.squares, pieceType, Color.WHITE, null);
        ChessboardService.move(game.chessboard, squares.get(0).getPosX(), squares.get(0).getPosY(), 2, 0);
        HashSet<Move> possibleMoves = game.getChessboard().getSquares()[2][0].getPiece().getAllowedFullMoves(game);
        System.out.println(possibleMoves);

        HashSet<Position> positions = new HashSet<>();
        for (Move move :
                possibleMoves) {
            positions.add(move.getToPos());
        }
        return positions;
    }

    @Test
    void testPawn() {

        HashSet<Position> possibleMovePositions = getPossibleMovePositions(PieceType.PAWN);

        // One Left
        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
        // One Right
        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
        // Multiple Right
        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
        // One Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 23)));
        // Two Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 22)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertFalse(possibleMovePositions.contains(new Position(3, 23)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 23)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 22)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testKing() {

        HashSet<Position> possibleMovePositions = getPossibleMovePositions(PieceType.KING);

        // One Left
        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
        // One Right
        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
        // Multiple Right
        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
        // One Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 23)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertTrue(possibleMovePositions.contains(new Position(3, 23)));
        // One diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(1, 23)));
        // One diagonal backward left
        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 22)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testQueen() {

        HashSet<Position> possibleMovePositions = getPossibleMovePositions(PieceType.QUEEN);

        // One Left
        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
        // One Right
        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
        // Multiple Right
        assertTrue(possibleMovePositions.contains(new Position(0, 0)));
        // One Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
        // Multiple Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 2)));
        // Multiple Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 16)));
        // One Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 23)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertTrue(possibleMovePositions.contains(new Position(3, 23)));
        // One diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(1, 23)));
        // One diagonal backward left
        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(0, 22)));
        // Multiple diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testBishop() {

        HashSet<Position> possibleMovePositions = getPossibleMovePositions(PieceType.BISHOP);

        // One Left
        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
        // One Right
        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
        // Multiple Right
        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
        // One Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 23)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertTrue(possibleMovePositions.contains(new Position(3, 23)));
        // One diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(1, 23)));
        // One diagonal backward left
        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(0, 22)));
        // Multiple diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testRook() {

        HashSet<Position> possibleMovePositions = getPossibleMovePositions(PieceType.ROOK);

        // One Left
        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
        // One Right
        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
        // Multiple Right
        assertTrue(possibleMovePositions.contains(new Position(0, 0)));
        // One Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
        // Multiple Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 2)));
        // Multiple Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 16)));
        // One Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 23)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertFalse(possibleMovePositions.contains(new Position(3, 23)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 23)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 22)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }


}