package com.chess.backend.services;

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

    private static ArrayList<ArrayList<Square>> initEmptySquares(Integer numberOfPlayers){
        int boardWidth = 5;
        ArrayList<ArrayList<Square>>squares = new ArrayList<>(boardWidth);
        for(int i =0; i<boardWidth; i++){
            int boardLength = numberOfPlayers * 9;
            ArrayList<Square> emptySquares = new ArrayList<>(boardLength);
            for (int squarePos = 0; squarePos < boardLength; squarePos++) {
                emptySquares.add(new Square(i, squarePos, null));
            }

            squares.add(emptySquares);

        }
        return squares;
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
        ArrayList<ArrayList<Square>> squares = initEmptySquares(chessboard.getNumberOfPlayers());
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
    private static void initPlayerPieces(ArrayList<ArrayList<Square>> squares, Player player) {
        initPlayerPawns(squares, player);
        initPlayerFigures(squares, player);
    }

    /**
     * init Pawns pieces for one player
     *
     * @param squares 2D Array  of Square object
     * @param player  Player object
     */
    private static void initPlayerPawns(ArrayList<ArrayList<Square>> squares, Player player) {
        int figuresFirstColumn = PlayerService.getBaseY(player);
        for (int x = 0; x < squares.size(); x++) {
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
    private static void initPlayerFigures(ArrayList<ArrayList<Square>> squares, Player player) {
        int figuresFirstColumn = PlayerService.getBaseY(player);

        // anticlockwise
        setPiece(0, figuresFirstColumn + 1, squares, new Queen(player, false));
        setPiece(1, figuresFirstColumn + 1, squares, new Bishop(player, false));
        setPiece(2, figuresFirstColumn + 1, squares, new Knight(player, false));
        setPiece(3, figuresFirstColumn + 1, squares, new Rook(player, false));
        setPiece(4, figuresFirstColumn + 1, squares, new Ferz(player, false));

        // clockwise
        setPiece(0, figuresFirstColumn + 2, squares, new King(player, true));
        setPiece(1, figuresFirstColumn + 2, squares, new Bishop(player, true));
        setPiece(2, figuresFirstColumn + 2, squares, new Knight(player, true));
        setPiece(3, figuresFirstColumn + 2, squares, new Rook(player, true));
        setPiece(3, figuresFirstColumn + 2, squares, new Wazir(player, true));

        //cannon
        Player dummyCanonPlayer = new Player();
        dummyCanonPlayer.setName("The canon");
        setPiece(2, figuresFirstColumn + 6, squares, new Cannon(dummyCanonPlayer, true));
    }

    /**
     * private function that takes two positions, ie x,y and set piece in Square[x][y]
     *
     * @param posX    position x
     * @param posY    position y
     * @param squares 2D array of Square object
     * @param piece   Piece object
     */
    public static void setPiece(int posX, int posY, ArrayList<ArrayList<Square>> squares, Piece piece) {

        Square square = squares.get(posX).get(posY);
        piece.setPosY(posY);
        piece.setPosX(posX);

        square.setPiece(piece);
        squares.get(posX).set(posY, square);

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
    public static ArrayList<Square> searchSquaresByPiece(ArrayList<ArrayList<Square>> squares, PieceType pieceType, Color color, Player player) {
        ArrayList<Square> result = new ArrayList<>();

        ArrayList<Square> inputSquares = new ArrayList<>();
        for (ArrayList<Square> squareArray :
                squares) {
            inputSquares.addAll(squareArray);
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

    /**
     * return list of Squares with pieces on it
     *
     * @param squares 2D Array of Square object
     * @return ArrayList of Square object
     */
    public static ArrayList<Square> getOccupiedSquares(ArrayList<ArrayList<Square>> squares) {
        return searchSquaresByPiece(squares, null, null, null);
    }

    // Annulus perimeter
    public static int getMaxY(ArrayList<ArrayList<Square>> squares) { //TODO check
        return squares.get(0).size() - 1;
    }

    // Annulus width
    public static int getMaxX(ArrayList<ArrayList<Square>> squares) {
        return squares.size() - 1;
    }

    // Replaces Chessboard.bottom field
    public static int getBottom(ArrayList<ArrayList<Square>>squares) {
        return 0;
    }

    /**
     * Replaces Chessboard.top field
     *
     * @param squares
     * @return get
     */
    public static int getTop(ArrayList<ArrayList<Square>> squares) {
        return getMaxY(squares);
    }

    /**
     * Apply move to chessboard
     *
     * @param chessboard Chessboard object
     * @param move       Move object
     */
    public static void move(Chessboard chessboard, Move move) {
        Piece piece = move.getMovedPiece();

        chessboard.getSquares().get(move.getTo().getPosX()).get(move.getTo().getPosY()).setPiece((Piece)piece);
//                [move.getTo().getPosX()][move.getTo().getPosY()]
//                .setPiece(piece);
        chessboard.getSquares().get(move.getFrom().getPosX()).get(move.getFrom().getPosY()).removePiece();
//                [move.getFrom().getPosX()][move.getFrom().getPosY()]
//                .removePiece();

        if(piece.getType() == PieceType.PAWN){
            rankUpPawn((Pawn) piece, move.getFrom().getPosY(), move.getTo().getPosY(), getChessboardLength(chessboard));

            if(checkPawnPromotion((Pawn) piece)){
                promotePawn(chessboard, piece);
            }
        }
    }

    public static void move(Chessboard chessboard, int fromX, int fromY, int toX, int toY) {
        Piece piece = chessboard.getSquares().get(fromX).get(fromY).getPiece();

        chessboard.getSquares().get(toX).get(toY).setPiece((Piece)piece);
        chessboard.getSquares().get(fromX).get(fromY).removePiece();
        if (piece.getType() != PieceType.CANNON) {
            chessboard.getSquares()
                    .get(toX).get(toY).setPiece((Piece)piece);
            chessboard.getSquares().get(fromX).get(fromY).removePiece();
        } else {
            chessboard.getSquares().get(toX).get(toY).removePiece();
        }
        if(piece.getType() == PieceType.PAWN){
            rankUpPawn((Pawn) piece, fromY, toY, getChessboardLength(chessboard));

            if(checkPawnPromotion((Pawn) piece)){
                promotePawn(chessboard, piece);
            }
        }
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
        return chessboard.getSquares().get(position.getX()).get(position.getY());
//                [position.getX()][position.getY()];
    }

    public static void initClean(Chessboard chessboard) {
        ArrayList<ArrayList<Square>> squares = initEmptySquares(chessboard.getNumberOfPlayers());
        initClean(squares);
        chessboard.setSquares(squares);
    }

    public static void initClean(ArrayList<ArrayList<Square>>squares){
        for (int y = 0; y < squares.get(0).size(); y++) {
            for (int x = 0; x < squares.size(); x++) {
                squares.get(x).set(y, new Square(x, y, null));
//                squares[x][y] = new Square(x, y, null);
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

    public static Piece getPieceByPosition(Chessboard chessboard, int x, int y){
        return chessboard.getSquares().get(x).get(y).getPiece();
    }

    public static Piece getPieceByPosition(Chessboard chessboard, Position position){
        return chessboard.getSquares().get(position.getX()).get(position.getY()).getPiece();
    }

    private static void rankUpPawn(Pawn pawn, int posFrom, int posTo, int chessboardLength){
        if((Math.abs(posFrom-posTo) == 1) || ((Math.abs(posTo-posFrom)-chessboardLength) == -1)){
            pawn.setRank(pawn.getRank()+1);
            System.out.println("Pawn Rank: " + pawn.getRank());
        } else {
            if((Math.abs(posFrom-posTo) == 2) || ((Math.abs(posTo-posFrom)-chessboardLength) == -2)){
                pawn.setRank(pawn.getRank()+2);
                System.out.println("Pawn Rank: " + pawn.getRank());
            }
        }
    }

    private static int getChessboardLength(final Chessboard chessboard) {
        return chessboard.getSquares().get(0).size();
    }

    private static boolean checkPawnPromotion(Pawn pawn){
        return pawn.getRank() == 8;
    }

    private static void promotePawn(Chessboard chessboard, Piece piece){
        chessboard.getSquares().get(piece.getPosX()).get(piece.getPosY()).removePiece();
        setPiece(piece.getPosX(), piece.getPosY(), chessboard.getSquares(), new Queen(piece.getPlayer(), piece.isClockwise()));
    }
}
