package com.chess.backend.services;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ChessboardService {

    public static Chessboard initNewGameBoard(Player[] players) {
        Chessboard chessboard = new Chessboard();
        chessboard.setNumberOfPlayers(players.length);
        Square[][] squares = new Square[4][players.length * 8];

        for (int y = 0; y < squares[0].length; y++) {
            for (int x = 0; x < squares.length; x++) {
                squares[x][y] = new Square(x, y, null);
            }
        }

        for (Player player :
                players) {
            initPlayerPieces(squares, player);
        }

        chessboard.setSquares(squares);
        return chessboard;
    }

    private static void initPlayerPieces(Square[][] squares, Player player) {
        initPlayerPawns(squares, player);
        initPlayerFigures(squares, player);
    }

    private static void initPlayerPawns(Square[][] squares, Player player) {
        int playerFirstColumn = player.getId() * 8;
        for (int i = 0; i < squares.length; i++) {
            setPiece(i, playerFirstColumn, squares, new Piece(PieceType.PAWN, player, true));
            setPiece(i, playerFirstColumn + 3, squares, new Piece(PieceType.PAWN, player, false));
        }
    }

    private static void initPlayerFigures(Square[][] squares, Player player) {
        int figuresFirstColumn = player.getId() * 8 + 1;

        for (int i = 0; i < 2; i++) {
            setPiece(2, figuresFirstColumn + i, squares, new Piece(PieceType.KNIGHT, player, true));
            setPiece(3, figuresFirstColumn + i, squares, new Piece(PieceType.ROOK, player, true));
//          this.setPiece(i, player.getId() + 3, squares, new Piece(PieceType.PAWN, player));
        }
        setPiece(3, figuresFirstColumn, squares, new Piece(PieceType.KING, player, true));
        setPiece(3, figuresFirstColumn + 1, squares, new Piece(PieceType.QUEEN, player, true));
    }

    private static void setPiece(int pozX, int pozY, Square[][] squares, Piece piece) {
        Square square = squares[pozX][pozY];
        square.setPiece(piece);
        squares[pozX][pozY] = square;
    }

    public static ArrayList<Square> searchSquaresByPiece(Square[][] squares, PieceType pieceType, Color color, Player player) {
        ArrayList<Square> result = new ArrayList<>();

        ArrayList<Square> inputSquares = new ArrayList<>();
        for (Square[] squareArray :
                squares) {
            inputSquares.addAll(Arrays.asList(squareArray));
        }

        for (Square square :
                inputSquares) {
            Piece piece = square.getPiece();
            if (piece.getType() == pieceType
                    && piece.getColor() == color
                    && piece.getPlayer() == player) {
                result.add(square);
            }
        }
        return result;
    }

}
