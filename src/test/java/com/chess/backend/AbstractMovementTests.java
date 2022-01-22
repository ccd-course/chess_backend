package com.chess.backend;

import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.gamemodel.pieces.*;
import com.chess.backend.services.ChessboardService;
import com.chess.backend.services.ChessGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractMovementTests {

    ChessGame game;

    @BeforeEach
    void setUp() {
        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
        ChessGameService chessGameService = new ChessGameService();
        chessGameService.createNewGame(players);
        game = chessGameService.getGame();
    }

    void setUpChessboard(IPiece piece) {
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(2, 0,
                game.getChessboard().getSquares(),
                piece);
    }

    void spawnPawnAsVictim(Position position) {
        ChessboardService.setPiece(position.getX(), position.getY(),
                game.getChessboard().getSquares(),
                new Pawn(game.getPlayers().get(0), true));
    }

    HashSet<Position> getPossibleMovePositions(PieceType pieceType) {
        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
                game.getChessboard().getSquares(), pieceType, Color.WHITE, null);
        //ChessboardService.move(game.chessboard, squares.get(0).getPosX(), squares.get(0).getPosY(), 2, 0);
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

        PieceType pieceType = PieceType.PAWN;
        IPiece piece = new Pawn(game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 23));
        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);

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
        // One diagonal forward left (here is the other pawn as victim)
        assertTrue(possibleMovePositions.contains(new Position(3, 23)));
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

        PieceType pieceType = PieceType.KING;

        IPiece piece = new King(game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 23));
        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);

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

        PieceType pieceType = PieceType.QUEEN;

        IPiece piece = new Queen(game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 23));
        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);

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
        // Knight move FL
        assertFalse(possibleMovePositions.contains(new Position(3, 22)));
        // Knight move FR
        assertFalse(possibleMovePositions.contains(new Position(1, 22)));
        // Knight move BL
        assertFalse(possibleMovePositions.contains(new Position(3, 2)));
        // Knight move BR
        assertFalse(possibleMovePositions.contains(new Position(1, 2)));

    }

    @Test
    void testBishop() {

        PieceType pieceType = PieceType.BISHOP;

        IPiece piece = new Bishop(game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 23));
        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);

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

        PieceType pieceType = PieceType.ROOK;

        IPiece piece = new Rook(game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(2, 5));
        spawnPawnAsVictim(new Position(2, 13));
        spawnPawnAsVictim(new Position(2, 14));
        spawnPawnAsVictim(new Position(2, 16));
        spawnPawnAsVictim(new Position(2, 17));
        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);

        // One Left
        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
        // One Right
        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
        // Multiple Right
        assertTrue(possibleMovePositions.contains(new Position(0, 0)));
        // One Backward
        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
        // Multiple Backward (here is the pawn victim)
        assertTrue(possibleMovePositions.contains(new Position(2, 5)));
        // Multiple Backward (behind captured pawn)
        assertFalse(possibleMovePositions.contains(new Position(2, 7)));
        // One Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 23)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 20)));
        // Multiple Forward (between pawns)
        assertFalse(possibleMovePositions.contains(new Position(2, 15)));
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
    void testKnight() {

        PieceType pieceType = PieceType.KNIGHT;

        IPiece piece = new Knight(game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 21));
        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);

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
        // Knight move FL
        assertTrue(possibleMovePositions.contains(new Position(3, 22)));
        // Knight move FR
        assertTrue(possibleMovePositions.contains(new Position(1, 22)));
        // Knight move BL
        assertTrue(possibleMovePositions.contains(new Position(3, 2)));
        // Knight move BR
        assertTrue(possibleMovePositions.contains(new Position(1, 2)));

    }


}