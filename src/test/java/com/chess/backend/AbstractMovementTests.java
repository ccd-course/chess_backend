//package com.chess.backend;
//
//import com.chess.backend.gamemodel.*;
//import com.chess.backend.gamemodel.constants.Color;
//import com.chess.backend.gamemodel.constants.PieceType;
//import com.chess.backend.gamemodel.pieces.*;
//import com.chess.backend.services.ChessboardService;
//import com.chess.backend.services.ChessGameService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AbstractMovementTests {
//
//    ChessGame game;
//
//    @BeforeEach
//    void setUp() {
//        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
//        ChessGameService chessGameService = new ChessGameService();
//        chessGameService.createNewGame(players);
//        game = chessGameService.getGame();
//    }
//
//    void setUpChessboard(Piece piece) {
//        ChessboardService.initClean(game.getChessboard());
//        ChessboardService.setPiece(2, 0,
//                game.getChessboard().getSquares(),
//                piece);
//    }
//
//    void spawnPawnAsVictim(Position position) {
//        ChessboardService.setPiece(position.getX(), position.getY(),
//                game.getChessboard().getSquares(),
//                new Pawn(game.getPlayers().get(1), true));
//    }
//
//    HashSet<Position> getPossibleMovePositions(PieceType pieceType) {
////<<<<<<< HEAD
//        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
//                game.getChessboard().getSquares(), pieceType, Color.WHITE, null);
//        //ChessboardService.move(game.chessboard, squares.get(0).getPosX(), squares.get(0).getPosY(), 2, 0);
//        HashSet<Move> possibleMoves = game.getChessboard().getSquares().get(2).get(0).getPiece().getAllowedFullMoves(game);
////||||||| 2414c43
////        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
////                game.getChessboard().getSquares(), pieceType, Color.WHITE, null);
////        //ChessboardService.move(game.chessboard, squares.get(0).getPosX(), squares.get(0).getPosY(), 2, 0);
////        HashSet<Move> possibleMoves = game.getChessboard().getSquares()[2][0].getPiece().getAllowedFullMoves(game);
////=======
////        HashSet<Move> possibleMoves = game.getChessboard().getSquares()[2][0].getPiece().getAllowedFullMoves(game);
////>>>>>>> 0fcffd5addade627c1b3b8bdf4b6a048f8ba3079
//        System.out.println(possibleMoves);
//
//        HashSet<Position> positions = new HashSet<>();
//        for (Move move :
//                possibleMoves) {
//            if (pieceType == PieceType.CANNON){
//                positions.add(move.getTakenPos());
//            } else {
//                positions.add(move.getToPos());
//            }
//        }
//        return positions;
//    }
//
//    @Test
//    void testPawn() {
//
//        PieceType pieceType = PieceType.PAWN;
//        Piece piece = new Pawn(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(3, 26));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 26)));
//        // Two Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 25)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left (here is the other pawn as victim)
//        assertTrue(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
//
//    }
//
//    @Test
//    void testKing() {
//
//        PieceType pieceType = PieceType.KING;
//
//        Piece piece = new King(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(3, 26));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
//
//    }
//
//    @Test
//    void testQueen() {
//
//        PieceType pieceType = PieceType.QUEEN;
//
//        Piece piece = new Queen(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(3, 26));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertTrue(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertTrue(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertTrue(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertTrue(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertTrue(possibleMovePositions.contains(new Position(0, 2)));
//        // Knight move FL
//        assertFalse(possibleMovePositions.contains(new Position(3, 25)));
//        // Knight move FR
//        assertFalse(possibleMovePositions.contains(new Position(1, 25)));
//        // Knight move BL
//        assertFalse(possibleMovePositions.contains(new Position(3, 2)));
//        // Knight move BR
//        assertFalse(possibleMovePositions.contains(new Position(1, 2)));
//
//    }
//
//    @Test
//    void testBishop() {
//
//        PieceType pieceType = PieceType.BISHOP;
//
//        Piece piece = new Bishop(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(3, 26));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertTrue(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertTrue(possibleMovePositions.contains(new Position(0, 2)));
//
//    }
//
//    @Test
//    void testRook() {
//
//        PieceType pieceType = PieceType.ROOK;
//
//        Piece piece = new Rook(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(2, 5));
//        spawnPawnAsVictim(new Position(2, 13));
//        spawnPawnAsVictim(new Position(2, 14));
//        spawnPawnAsVictim(new Position(2, 16));
//        spawnPawnAsVictim(new Position(2, 17));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertTrue(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward (here is the pawn victim)
//        assertTrue(possibleMovePositions.contains(new Position(2, 5)));
//        // Multiple Backward (behind captured pawn)
//        assertFalse(possibleMovePositions.contains(new Position(2, 7)));
//        // One Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 20)));
//        // Multiple Forward (between pawns)
//        assertFalse(possibleMovePositions.contains(new Position(2, 15)));
//        // One diagonal forward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
//
//    }
//
//    @Test
//    void testKnight() {
//
//        PieceType pieceType = PieceType.KNIGHT;
//
//        Piece piece = new Knight(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(3, 21));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
//        // Knight move FL
//        assertTrue(possibleMovePositions.contains(new Position(3, 25)));
//        // Knight move FR
//        assertTrue(possibleMovePositions.contains(new Position(1, 25)));
//        // Knight move BL
//        assertTrue(possibleMovePositions.contains(new Position(3, 2)));
//        // Knight move BR
//        assertTrue(possibleMovePositions.contains(new Position(1, 2)));
//
//    }
//
//    @Test
//    void testCannon() {
//
//        PieceType pieceType = PieceType.CANNON;
//
//        Piece piece = new Cannon(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        Piece ownPiece1 = new Pawn(game.getPlayers().get(0), true);
//        Piece ownPiece2 = new Pawn(game.getPlayers().get(0), true);
//        ChessboardService.setPiece(2, 1,
//                game.getChessboard().getSquares(),
//                ownPiece1);
//        ChessboardService.setPiece(3, 1,
//                game.getChessboard().getSquares(),
//                ownPiece2);
//        spawnPawnAsVictim(new Position(0, 25));
//        spawnPawnAsVictim(new Position(2, 21));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertTrue(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
//        // Knight move FL
//        assertFalse(possibleMovePositions.contains(new Position(3, 25)));
//        // Knight move FR
//        assertFalse(possibleMovePositions.contains(new Position(1, 25)));
//        // Knight move BL
//        assertFalse(possibleMovePositions.contains(new Position(3, 2)));
//        // Knight move BR
//        assertFalse(possibleMovePositions.contains(new Position(1, 2)));
//        // Cannon straight
//        assertTrue(possibleMovePositions.contains(new Position(2, 21)));
//        // Cannon diagonal
//        assertTrue(possibleMovePositions.contains(new Position(0, 25)));
//
//        // Cannon may not shoot if another player is in neighborhood
//        spawnPawnAsVictim(new Position(2, 26));
//        possibleMovePositions = getPossibleMovePositions(pieceType);
//        assertEquals(possibleMovePositions.size(), 0);
//    }
//
//    @Test
//    void testFerz() {
//
//        PieceType pieceType = PieceType.FERZ;
//
//        Piece piece = new Ferz(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(3, 26));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertFalse(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertFalse(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertTrue(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertTrue(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
//
//    }
//
//    @Test
//    void testWazir() {
//
//        PieceType pieceType = PieceType.WAZIR;
//
//        Piece piece = new Wazir(game.getPlayers().get(0), true);
//        setUpChessboard(piece);
//        spawnPawnAsVictim(new Position(3, 26));
//        HashSet<Position> possibleMovePositions = getPossibleMovePositions(pieceType);
//
//        // One Left
//        assertTrue(possibleMovePositions.contains(new Position(3, 0)));
//        // One Right
//        assertTrue(possibleMovePositions.contains(new Position(1, 0)));
//        // Multiple Right
//        assertFalse(possibleMovePositions.contains(new Position(0, 0)));
//        // One Backward
//        assertTrue(possibleMovePositions.contains(new Position(2, 1)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 2)));
//        // Multiple Backward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One Forward
//        assertTrue(possibleMovePositions.contains(new Position(2, 26)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 12)));
//        // Multiple Forward
//        assertFalse(possibleMovePositions.contains(new Position(2, 16)));
//        // One diagonal forward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 26)));
//        // One diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 26)));
//        // One diagonal backward left
//        assertFalse(possibleMovePositions.contains(new Position(3, 1)));
//        // One diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(1, 1)));
//        // Multiple diagonal forward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 25)));
//        // Multiple diagonal backward right
//        assertFalse(possibleMovePositions.contains(new Position(0, 2)));
//
//    }
//}