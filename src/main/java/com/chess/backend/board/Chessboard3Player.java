package com.chess.backend.board;

import com.chess.backend._Player._Player;

import java.awt.*;

public class Chessboard3Player extends Chessboard{

    public Chessboard3Player(final Image chessboardImage, final Square[] chessboardSquares, _Player[] player,
                             int currentPlayer){
        super(chessboardImage,chessboardSquares,player,currentPlayer);
    }
}
