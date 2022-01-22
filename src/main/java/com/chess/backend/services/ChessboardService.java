package com.chess.backend.services;

import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.gamemodel.pieces.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stateless Chessboard Service to initialize new Chessboard
 * and does operations on Chessboard object
 */
@Component
public class ChessboardService {

    public ChessboardService() {
    }

    /**
     * create new Chessboard from Players object
     *
     * @param players Array of Player object
     * @return Chessboard object
     */
    public static Chessboard initNewGameBoard(List<Player> players) {
        Chessboard chessboard = new Chessboard();
        chessboard.setNumberOfPlayers(players.size());
        Square[][] squares = new Square[4][chessboard.getNumberOfPlayers() * 8];
        initClean(squares);

        for (Player player :
                players) {
            initPlayerPieces(squares, player);
        }

        chessboard.setSquares(squares);
        return chessboard;
    }

    /**
     * private function to set pieces for one player
     *
     * @param squares 2D Array  of Square object
     * @param player  Player object
     */
    private static void initPlayerPieces(Square[][] squares, Player player) {
        initPlayerPawns(squares, player);
        initPlayerFigures(squares, player);
    }

    /**
     * init Pawns pieces for one player
     *
     * @param squares 2D Array  of Square object
     * @param player  Player object
     */
    private static void initPlayerPawns(Square[][] squares, Player player) {
        int figuresFirstColumn = PlayerService.getBaseY(player);
        for (int x = 0; x < squares.length; x++) {
            setPiece(x, figuresFirstColumn, squares, new Pawn(player, true));
            setPiece(x, figuresFirstColumn + 3, squares, new Pawn(player, false));
        }
    }

    /**
     * init figures pieces for one player
     *
     * @param squares 2D Array  of Square object
     * @param player  Player object
     */
    private static void initPlayerFigures(Square[][] squares, Player player) {
        int figuresFirstColumn = PlayerService.getBaseY(player);

        // anticlockwise
        setPiece(0, figuresFirstColumn + 1, squares, new Queen(player, false));
        setPiece(1, figuresFirstColumn + 1, squares, new Bishop(player, false));
        setPiece(2, figuresFirstColumn + 1, squares, new Knight(player, false));
        setPiece(3, figuresFirstColumn + 1, squares, new Rook(player, false));

        // clockwise
        setPiece(0, figuresFirstColumn + 2, squares, new King(player, true));
        setPiece(1, figuresFirstColumn + 2, squares, new Bishop(player, true));
        setPiece(2, figuresFirstColumn + 2, squares, new Knight(player, true));
        setPiece(3, figuresFirstColumn + 2, squares, new Rook(player, true));
    }

    /**
     * private function that takes two positions, ie x,y and set piexe in Square[x][y]
     *
     * @param posX    position x
     * @param posY    position y
     * @param squares 2D array of Square object
     * @param piece   Piece object
     */
    public static void setPiece(int posX, int posY, Square[][] squares, IPiece piece) {
        Square square = squares[posX][posY];
        square.setPiece(piece);
        squares[posX][posY] = square;
    }

    /**
     * filter squares according to piece type, color or player
     *
     * @param squares   2D Square Array
     * @param pieceType PieceType object
     * @param color     Color object
     * @param player    Player object
     * @return ArrayList of Square object
     */
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
            IPiece piece = square.getPiece();
            if ((pieceType == null || piece.getType() == pieceType)
                    && (color == null || piece.getColor() == color)
                    && (player == null || piece.getPlayer() == player)) {
                result.add(square);
            }
        }
        return result;
    }

    /**
     * return list of Squares with pieces on it
     *
     * @param squares 2D Array of Square object
     * @return ArrayList of Square object
     */
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

    /**
     * Replaces Chessboard.top field
     *
     * @param squares
     * @return get
     */
    public static int getTop(Square[][] squares) {
        return getMaxY(squares);
    }

    /**
     * Apply move to chessboard
     *
     * @param chessboard Chessboard object
     * @param move       Move object
     */
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

    /**
     * gets Position object and Chessboard and returns the matched Square object
     * to this position.
     *
     * @param chessboard Chessboard object
     * @param position   Position object
     * @return matched Square object
     */
    public static Square getSquare(Chessboard chessboard, Position position) {
        return chessboard.getSquares()[position.getX()][position.getY()];
    }

    public static void initClean(Chessboard chessboard) {
        Square[][] squares = new Square[4][chessboard.getNumberOfPlayers() * 8];
        initClean(squares);
        chessboard.setSquares(squares);
    }

    public static void initClean(Square[][] squares){
        for (int y = 0; y < squares[0].length; y++) {
            for (int x = 0; x < squares.length; x++) {
                squares[x][y] = new Square(x, y, null);
            }
        }
    }

    public static void removeOtherPieceTypes(Chessboard chessboard, PieceType pieceType) {
        ArrayList<Square> squares = ChessboardService.getOccupiedSquares(chessboard.getSquares());
        for (Square square :
                squares) {
            if (square.getPieceTypeOfPiece() != pieceType) {
                square.removePiece();
            }
        }
    }

    public static IPiece getPieceByPosition(Chessboard chessboard, int x, int y){
        return chessboard.getSquares()[x][y].getPiece();
    }
}
