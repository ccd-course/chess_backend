package com.chess.backend.services;

import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.constants.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ChessboardServiceTests {
    @Test
    void testNumberOfPlayers() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(players);
        assertEquals(chessboard.getNumberOfPlayers(), 3);

    }
    @Test
    void testChessboardSize() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(players);
        assertEquals(chessboard.getSquares().length, 4);
        assertEquals(chessboard.getSquares()[0].length, 24);

    }
}
