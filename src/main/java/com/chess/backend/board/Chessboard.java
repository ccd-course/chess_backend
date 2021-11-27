package com.chess.backend.board;

import com.chess.backend._Player._Player;
import com.chess.backend.pieces.Piece;

import java.awt.*;


/**
 * A class responsible for displaying the board, controll the logic of the game.
 * The Events generated from this class will be passed back to the GameControllerClass.
 * In order to display them in other widget, or storing the game.
 * More function will be added later to this class
 */
public abstract class Chessboard {

    private final Image chessboardImage;
    private final Square[] chessboardSquares;
    private Piece[][] playersPieces;

    public Chessboard(final Image chessboardImage, final Square[] chessboardSquares, _Player[] player,
                      int currentPlayer){
        this.chessboardImage = chessboardImage;
        this.chessboardSquares = chessboardSquares;
    }

}
