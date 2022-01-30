package com.chess.backend;

import com.chess.backend.domain.repository.IGameRepository;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.repository.GameRepositoryMock;
import com.chess.backend.services.ChessboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractMovementTests {

    ChessGame game;
    IGameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepositoryMock();
        game = ChessGameInitializationTests.createNewTestGame(gameRepository);
    }

    void setUpChessboard(Piece piece) {
        ChessboardService.initClean(game.getChessboard());
        ChessboardService.setPiece(2, 0,
                game.getChessboard().getSquares(),
                piece);
    }

    void spawnPawnAsVictim(Position position) {
        ChessboardService.setPiece(position.getX(), position.getY(),
                game.getChessboard().getSquares(),
                new Piece(PieceType.PAWN, game.getPlayers().get(1), true));
    }

    HashSet<Position> getPossibleMovePositions(PieceType pieceType) {
        HashSet<Move> possibleMoves = game.getChessboard().getSquares().get(2).get(0).getPiece().getAllowedFullMoves(game.getChessboard());
        System.out.println(possibleMoves);

        HashSet<Position> positions = new HashSet<>();
        for (Move move :
                possibleMoves) {
            if (pieceType == PieceType.CANNON){
                positions.add(move.getTakenPos());
            } else {
                positions.add(move.getToPos());
            }
        }
        return positions;
    }

    @Test
    void testPawn() {

        PieceType pieceType = PieceType.PAWN;
        Piece piece = new Piece(PieceType.PAWN, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 29));
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
        assertTrue(possibleMovePositions.contains(new Position(2, 29)));
        // Two Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 28)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left (here is the other pawn as victim)
        assertTrue(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testKing() {

        PieceType pieceType = PieceType.KING;

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 29));
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
        assertTrue(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertTrue(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testQueen() {

        PieceType pieceType = PieceType.QUEEN;

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 29));
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
        assertTrue(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertTrue(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(0, 2)));
        // Knight move FL
        assertFalse(possibleMovePositions.contains(new Position(3, 28)));
        // Knight move FR
        assertFalse(possibleMovePositions.contains(new Position(1, 28)));
        // Knight move BL
        assertFalse(possibleMovePositions.contains(new Position(3, 2)));
        // Knight move BR
        assertFalse(possibleMovePositions.contains(new Position(1, 2)));

    }

    @Test
    void testBishop() {

        PieceType pieceType = PieceType.BISHOP;

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 29));
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
        assertFalse(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertTrue(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testRook() {

        PieceType pieceType = PieceType.ROOK;

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
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
        assertTrue(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertTrue(possibleMovePositions.contains(new Position(2, 20)));
        // Multiple Forward (between pawns)
        assertFalse(possibleMovePositions.contains(new Position(2, 15)));
        // One diagonal forward left
        assertFalse(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testKnight() {

        PieceType pieceType = PieceType.KNIGHT;

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(1, 28));
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
        assertFalse(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertFalse(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
        // Knight move FL
        assertTrue(possibleMovePositions.contains(new Position(3, 28)));
        // Knight move FR
        assertTrue(possibleMovePositions.contains(new Position(1, 28)));
        // Knight move BL
        assertTrue(possibleMovePositions.contains(new Position(3, 2)));
        // Knight move BR
        assertTrue(possibleMovePositions.contains(new Position(1, 2)));

    }

    @Test
    void testCannon() {
        PieceType pieceType = PieceType.CANNON;

        /*
        Part 1

        P = Pawn
        V = Victim

        Y  X 0 1 2 3 4
        ↓  ____________
        1  | # P P P #
        0  | # P C # V
        26 | # # # # #
        25 | V # # # V
        24 | # # # # #
        23 | # # # # #
        22 | # # # # #
        21 | # # V # #
         */

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        Piece ownPiece1 = new Piece(PieceType.PAWN, game.getPlayers().get(0), true);
        Piece ownPiece2 = new Piece(PieceType.PAWN, game.getPlayers().get(0), true);
        Piece ownPiece3 = new Piece(PieceType.PAWN, game.getPlayers().get(0), true);
        Piece ownPiece4 = new Piece(PieceType.PAWN, game.getPlayers().get(0), true);
        ChessboardService.setPiece(2, 1,
                game.getChessboard().getSquares(),
                ownPiece1);
        ChessboardService.setPiece(3, 1,
                game.getChessboard().getSquares(),
                ownPiece2);
        ChessboardService.setPiece(1, 1,
                game.getChessboard().getSquares(),
                ownPiece3);
        ChessboardService.setPiece(1, 0,
                game.getChessboard().getSquares(),
                ownPiece4);
        spawnPawnAsVictim(new Position(0, 28));
        spawnPawnAsVictim(new Position(2, 21));
        spawnPawnAsVictim(new Position(4, 28));
        spawnPawnAsVictim(new Position(4, 0));
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
        assertFalse(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertFalse(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
        // Knight move FL
        assertFalse(possibleMovePositions.contains(new Position(3, 28)));
        // Knight move FR
        assertFalse(possibleMovePositions.contains(new Position(1, 28)));
        // Knight move BL
        assertFalse(possibleMovePositions.contains(new Position(3, 2)));
        // Knight move BR
        assertFalse(possibleMovePositions.contains(new Position(1, 2)));
        // Cannon straight
        assertTrue(possibleMovePositions.contains(new Position(2, 21)));
        // Cannon diagonal
        assertTrue(possibleMovePositions.contains(new Position(0, 28)));
        // Cannon diagonal
        assertTrue(possibleMovePositions.contains(new Position(4, 28)));
        // Cannon left
        assertTrue(possibleMovePositions.contains(new Position(4, 0)));

        /*
        Part 2

        P = Pawn
        V = Victim

        Y  X 0 1 2 3 4
        ↓  ____________
        3  | # # V # #
        2  | V # # # V
        1  | # # # # #
        0  | V # C P #
        26 | # P P P #
        25 | # # # # #
         */

        setUpChessboard(piece);
        ChessboardService.setPiece(2, 29,
                game.getChessboard().getSquares(),
                ownPiece1);
        ChessboardService.setPiece(3, 29,
                game.getChessboard().getSquares(),
                ownPiece2);
        ChessboardService.setPiece(1, 29,
                game.getChessboard().getSquares(),
                ownPiece3);
        ChessboardService.setPiece(3, 0,
                game.getChessboard().getSquares(),
                ownPiece4);
        spawnPawnAsVictim(new Position(4, 2));
        spawnPawnAsVictim(new Position(2, 3));
        spawnPawnAsVictim(new Position(0, 2));
        spawnPawnAsVictim(new Position(0, 0));
        possibleMovePositions = getPossibleMovePositions(pieceType);

        // One Left
        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
        // One Right
        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
        // Multiple Right
        assertTrue(possibleMovePositions.contains(new Position(0, 0)));
        // One Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
        // Multiple Backward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertFalse(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(0, 2)));
        // Knight move FL
        assertFalse(possibleMovePositions.contains(new Position(3, 28)));
        // Knight move FR
        assertFalse(possibleMovePositions.contains(new Position(1, 28)));
        // Knight move BL
        assertFalse(possibleMovePositions.contains(new Position(3, 2)));
        // Knight move BR
        assertFalse(possibleMovePositions.contains(new Position(1, 2)));
        // Cannon straight
        assertTrue(possibleMovePositions.contains(new Position(4, 2)));
        // Cannon diagonal
        assertTrue(possibleMovePositions.contains(new Position(2, 3)));
        // Cannon diagonal
        assertTrue(possibleMovePositions.contains(new Position(0, 2)));
        // Cannon right
        assertTrue(possibleMovePositions.contains(new Position(0, 0)));

        // Cannon may not shoot if another player is in neighborhood
        spawnPawnAsVictim(new Position(2, 29));
        possibleMovePositions = getPossibleMovePositions(pieceType);
        assertEquals(possibleMovePositions.size(), 0);
    }

    @Test
    void testFerz() {

        PieceType pieceType = PieceType.FERZ;

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 29));
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
        assertFalse(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertTrue(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertTrue(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }

    @Test
    void testWazir() {

        PieceType pieceType = PieceType.WAZIR;

        Piece piece = new Piece(pieceType, game.getPlayers().get(0), true);
        setUpChessboard(piece);
        spawnPawnAsVictim(new Position(3, 29));
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
        assertTrue(possibleMovePositions.contains(new Position(2, 29)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
        // Multiple Forward
        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
        // One diagonal forward left
        assertFalse(possibleMovePositions.contains(new Position(3, 29)));
        // One diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(1, 29)));
        // One diagonal backward left
        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
        // One diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
        // Multiple diagonal forward right
        assertFalse(possibleMovePositions.contains(new Position(0, 28)));
        // Multiple diagonal backward right
        assertFalse(possibleMovePositions.contains(new Position(0, 2)));

    }
}