//package com.chess.backend;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.chess.backend.gamemodel.ChessGame;
//import com.chess.backend.gamemodel.Square;
//import com.chess.backend.gamemodel.constants.PieceType;
//import com.chess.backend.services.ChessboardService;
//import com.chess.backend.services.ChessGameService;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//public class ChessGameInitializationTests {
//
//    @Test
//    void testGameInitialization() {
//        String[] players = {"Test Player 1", "Test Player 2", "Test Player 3"};
//        ChessGameService chessGameService = new ChessGameService();
//        chessGameService.createNewGame(players);
//        ChessGame game = chessGameService.getGame();
//        ArrayList<Square> squares = ChessboardService.searchSquaresByPiece(
//                game.getChessboard().getSquares(), PieceType.KING, null, null);
//
//        assertTrue(squares.size() == game.getChessboard().getNumberOfPlayers());
//        System.out.println(squares.size());
//    }
//
//}
