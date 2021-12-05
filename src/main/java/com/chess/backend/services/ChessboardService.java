package com.chess.backend.services;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.PieceType;
import org.springframework.stereotype.Component;

@Component
public class ChessboardService {

    public Chessboard initNewGameBoard(String[] players){
        Chessboard chessboard = new Chessboard();
        chessboard.setNumberOfPlayers(players.length);
        Square[][] squares = new Square[10][4];

        for ( int i =0; i < 10;i++){
            for ( int j = 0 ; j <4; j++){
                squares[i][j] = new Square(i,j, null);
            }
        }
        for(int i=0;i< players.length; i++){
            Player player = new Player(players[i], i);
            this.initPlayerPieces(squares, player);
        }

        chessboard.setSquares(squares);
        return chessboard;
    }

    private Square[][] initPlayerPieces(Square[][] squares, Player player) {
        initPlayerPawns(squares, player);
        initPlayerFigures(squares, player);
        return squares;
    }

    private Square[][] initPlayerPawns(Square[][] squares, Player player) {
        int playerFirstColumn = player.getId() * 8;
        for(int i =0; i< squares.length;i++){
            this.setPiece(i, playerFirstColumn, squares, new Piece(PieceType.PAWN, player, true));
            this.setPiece(i, playerFirstColumn + 3, squares, new Piece(PieceType.PAWN, player, false));
        }
        return squares;
    }
    private Square[][] initPlayerFigures(Square[][] squares, Player player) {
        int figuresFirstColumn = player.getId() * 8 +1;

        for(int i =0; i< 2;i++){
            this.setPiece(2, figuresFirstColumn+i, squares, new Piece(PieceType.KNIGHT, player, true));
            this.setPiece(3, figuresFirstColumn+i, squares, new Piece(PieceType.ROOK, player, true));
//          this.setPiece(i, player.getId() + 3, squares, new Piece(PieceType.PAWN, player));
        }
        this.setPiece(3, figuresFirstColumn, squares, new Piece(PieceType.KING, player, true));
        this.setPiece(3, figuresFirstColumn+1, squares, new Piece(PieceType.QUEEN, player, true));

        return squares;
    }
    private Square[][] setPiece(int pozX, int pozY, Square[][] squares, Piece piece){
        Square square = squares[pozX][pozY];
        square.setPiece(piece);
        squares[pozX][pozY] = square;
        return squares;
    }

}
