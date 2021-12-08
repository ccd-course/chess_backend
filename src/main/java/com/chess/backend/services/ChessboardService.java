package com.chess.backend.services;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import org.springframework.stereotype.Component;

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
        int figuresFirstColumn = PlayerService.getBaseY(player);
        for (int x = 0; x < squares.length; x++) {
            setPiece(x, figuresFirstColumn, squares, new Piece(PieceType.PAWN, player, true));
            setPiece(x, figuresFirstColumn + 3, squares, new Piece(PieceType.PAWN, player, false));
        }
    }

    private static void initPlayerFigures(Square[][] squares, Player player) {
        int figuresFirstColumn = PlayerService.getBaseY(player);

        // anticlockwise
        setPiece(0, figuresFirstColumn + 1, squares, new Piece(PieceType.QUEEN, player, false));
        setPiece(1, figuresFirstColumn + 1, squares, new Piece(PieceType.BISHOP, player, false));
        setPiece(2, figuresFirstColumn + 1, squares, new Piece(PieceType.KNIGHT, player, false));
        setPiece(3, figuresFirstColumn + 1, squares, new Piece(PieceType.ROOK, player, false));

        // clockwise
        setPiece(0, figuresFirstColumn + 2, squares, new Piece(PieceType.KING, player, true));
        setPiece(1, figuresFirstColumn + 2, squares, new Piece(PieceType.BISHOP, player, true));
        setPiece(2, figuresFirstColumn + 2, squares, new Piece(PieceType.KNIGHT, player, true));
        setPiece(3, figuresFirstColumn + 2, squares, new Piece(PieceType.ROOK, player, true));
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
            if ((pieceType == null || piece.getType() == pieceType)
                    && (color == null || piece.getColor() == color)
                    && (player == null || piece.getPlayer() == player)) {
                result.add(square);
            }
        }
        return result;
    }

    public static ArrayList<Square> getOccupiedSquares(Square[][] squares) {
        return searchSquaresByPiece(squares, null, null, null);
    }

    // Annulus perimeter
    public static int getMaxY(Square[][] squares) { //TODO check
        return squares[0].length - 1;
    }

    // Annulus width
    public static int getMaxX(Square[][] squares) {
        return squares.length - 1;
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
                [move.getTo().getPosX()][move.getTo().getPosY()]
                .setPiece(move.getMovedPiece());
        chessboard.getSquares()
                [move.getFrom().getPosX()][move.getFrom().getPosY()]
                .removePiece();
    }

    public static void move(Chessboard chessboard, int fromX, int fromY, int toX, int toY) {
        chessboard.getSquares()
                [toX][toY]
                .setPiece(chessboard.getSquares()[fromX][fromY].getPiece());
        chessboard.getSquares()
                [fromX][fromY]
                .removePiece();
    }

    public static Square getSquare(Chessboard chessboard, Position position) {
        return chessboard.getSquares()[position.getX()][position.getY()];
    }

}
