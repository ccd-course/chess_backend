package com.chess.backend.board;

import com.chess.backend._Player._Player;

import java.awt.*;

public class Cheesboard2Player extends Chessboard{
    public Cheesboard2Player(final Image chessboardImage, final Square[] chessboardSquares, _Player[] player,
                      int currentPlayer){
        super(chessboardImage,chessboardSquares,player,currentPlayer);
    }
}
