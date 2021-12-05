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
        int playerFirstColumn = player.getColor().position * 8;
        for (int x = 0; x < squares.length; x++) {
            setPiece(x, playerFirstColumn, squares, new Piece(PieceType.PAWN, player, true));
            setPiece(x, playerFirstColumn + 3, squares, new Piece(PieceType.PAWN, player, false));
        }
    }

    private static void initPlayerFigures(Square[][] squares, Player player) {
        int figuresFirstColumn = player.getColor().position * 8;

        // anticlockwise
        setPiece(0, figuresFirstColumn + 1, squares, new Piece(PieceType.QUEEN, player, false));
        setPiece(1, figuresFirstColumn + 1, squares, new Piece(PieceType.BISHOP, player, false));
        setPiece(2, figuresFirstColumn + 1, squares, new Piece(PieceType.KNIGHT, player, false));
        setPiece(3, figuresFirstColumn + 1, squares, new Piece(PieceType.ROOK, player, false));

        // clockwise
        setPiece(0, figuresFirstColumn + 7, squares, new Piece(PieceType.KING, player, true));
        setPiece(1, figuresFirstColumn + 7, squares, new Piece(PieceType.BISHOP, player, true));
        setPiece(2, figuresFirstColumn + 7, squares, new Piece(PieceType.KNIGHT, player, true));
        setPiece(3, figuresFirstColumn + 7, squares, new Piece(PieceType.ROOK, player, true));
    }

    private static void setPiece(int posX, int posY, Square[][] squares, Piece piece) {
        Square square = squares[posX][posY];
        square.setPiece(piece);
        squares[posX][posY] = square;
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
            if (square.getPiece() == null) continue;
            Piece piece = square.getPiece();
            if (piece.getType() == pieceType
                    && piece.getColor() == color
                    && piece.getPlayer() == player) {
                result.add(square);
            }
        }
        return result;
    }

    // Annulus perimeter
    public static int getMaxY(Square[][] squares) { //TODO check
        return squares[0].length;
    }

    // Annulus width
    public static int getMaxX(Square[][] squares) {
        return squares.length;
    }

    // Replaces Chessboard.bottom field
    public static int getBottom(Square[][] squares) {
        return 0;
    }

    // Replaces Chessboard.top field
    public static int getTop(Square[][] squares) {
        return getMaxY(squares);
    }

    public static void move(Chessboard chessboard, Move move) {
        chessboard.getSquares()
                [move.getTo().getPozX()][move.getTo().getPozY()]
                .setPiece(move.getMovedPiece());
        chessboard.getSquares()
                [move.getFrom().getPozX()][move.getFrom().getPozY()]
                .removePiece();
    }

    public static void move(Chessboard chessboard, int fromX, int fromY, int toX, int toY) {
        chessboard.getSquares()
                [toX][toY]
                .setPiece(chessboard.getSquares()[toX][toY].getPiece());
        chessboard.getSquares()
                [fromX][fromY]
                .removePiece();
    }

}
