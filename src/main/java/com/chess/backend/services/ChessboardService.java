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
        Square[][] squares = new Square[5][chessboard.getNumberOfPlayers() * 9];
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
        IPiece piece = move.getMovedPiece();

        chessboard.getSquares()
                [move.getTo().getPosX()][move.getTo().getPosY()]
                .setPiece(piece);
        chessboard.getSquares()
                [move.getFrom().getPosX()][move.getFrom().getPosY()]
                .removePiece();

        if(piece.getType() == PieceType.PAWN){
            rankUpPawn((Pawn) piece, move.getFrom().getPosY(), move.getTo().getPosY(), getChessboardLength(chessboard));

            if(checkPawnPromotion((Pawn) piece)){
                promotePawn(chessboard, piece);
            }
        }
    }

    public static void move(Chessboard chessboard, int fromX, int fromY, int toX, int toY) {
        IPiece piece = chessboard.getSquares()[fromX][fromY].getPiece();

        if (piece.getType() != PieceType.CANNON) {
            chessboard.getSquares()
                    [toX][toY]
                    .setPiece(piece);
            chessboard.getSquares()
                    [fromX][fromY]
                    .removePiece();
        } else {
            chessboard.getSquares()
                    [toX][toY]
                    .removePiece();
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
        return chessboard.getSquares()[position.getX()][position.getY()];
    }

    public static void initClean(Chessboard chessboard) {
        Square[][] squares = new Square[5][chessboard.getNumberOfPlayers() * 9];
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

    public static IPiece getPieceByPosition(Chessboard chessboard, Position position){
        return chessboard.getSquares()[position.getX()][position.getY()].getPiece();
    }

    /**
     * Ranking up a pawn after a move.
     *
     * @param pawn The pawn to be ranked up.
     * @param posFrom The position the pawn was moved from.
     * @param posTo The position the pawn was moved to.
     * @param chessboardLength The length of the chessboard (y-axis).
     */
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

    /**
     * Ranking down a pawn after a move.
     *
     * @param pawn The pawn to be ranked down.
     * @param posFrom The position the pawn was moved from.
     * @param posTo The position the pawn was moved to.
     * @param chessboardLength The length of the chessboard (y-axis).
     */
    private static void rankDownPawn(Pawn pawn, int posFrom, int posTo, int chessboardLength){
        if((Math.abs(posFrom-posTo) == 1) || ((Math.abs(posTo-posFrom)-chessboardLength) == -1)){
            pawn.setRank(pawn.getRank()-1);
        } else {
            if((Math.abs(posFrom-posTo) == 2) || ((Math.abs(posTo-posFrom)-chessboardLength) == -2)){
                pawn.setRank(pawn.getRank()-2);
            }
        }
    }

    /**
     * Getting the length of a chessboard.
     * @param chessboard The chessboard.
     * @return Length of the chessboard.
     */
    private static int getChessboardLength(final Chessboard chessboard) {
        return chessboard.getSquares()[0].length;
    }

    /**
     * Check if a pawn has reached the maximum rank to be promoted.
     *
     * @param pawn The pawn that has to be checked.
     * @return True if the pawn has reached the maximum rank and false if not.
     */
    private static boolean checkPawnPromotion(Pawn pawn){
        return pawn.getRank() == 9;
    }

    /**
     * Promote a piece to a queen.
     *
     * @param chessboard The chessboard.
     * @param piece The piece that has to be promoted.
     */
    private static void promotePawn(Chessboard chessboard, IPiece piece){
        chessboard.getSquares()
                [piece.getSquare().getPosX()][piece.getSquare().getPosY()]
                .removePiece();

        setPiece(piece.getSquare().getPosX(), piece.getSquare().getPosY(), chessboard.getSquares(), new Queen(piece.getPlayer(), piece.isClockwise()));
    }

    /**
     * Gets all valid moves for a piece. Valid means that the move is possible for the piece and doesn't end up in a check for the piece owner.
     *
     * @param chessboard The chessboard.
     * @param piece      The piece for which the moves are to be determined.
     * @return A list of valid moves.
     */
    public static ArrayList<Square> getValidMovesForPiece(Chessboard chessboard, IPiece piece, Player player){
        ArrayList<Square> possibleMoves = piece.getAllowedMoves(chessboard);
        ArrayList<Square> validMoves = new ArrayList<>();

        for(Square square : possibleMoves){
            IPiece capturedPiece = square.getPiece();
            Square toSquare = square;
            Square fromSquare = piece.getSquare();

            if(!isCheck(simulateMove(chessboard, square, piece), player)){
                validMoves.add(square);
            }

            revertMove(chessboard, capturedPiece, piece, toSquare, fromSquare);
        }

        return validMoves;
    }

    /**
     * Revert a simulated move, so that the chessboard before the simulation is restored.
     *
     * @param chessboard The simulated chessboard.
     * @param capturedPiece A piece that was maybe captured during the move.
     * @param piece The piece that was moved.
     * @param to The position the piece was moved to.
     * @param from The position the piece was moved from.
     */
    private static void revertMove(Chessboard chessboard, IPiece capturedPiece, IPiece piece, Square to, Square from){
        if(piece.getType() == PieceType.CANNON){
            chessboard.getSquares()[to.getPosX()][to.getPosY()].setPiece(capturedPiece);
        } else {
            chessboard.getSquares()[from.getPosX()][from.getPosY()].setPiece(piece);
            chessboard.getSquares()[to.getPosX()][to.getPosY()].removePiece();

            //rank down a pawn
            if(piece.getType() == PieceType.PAWN){
                rankDownPawn((Pawn) piece, from.getPosY(), to.getPosY(), getChessboardLength(chessboard));
            }

            if(capturedPiece != null){
                chessboard.getSquares()[to.getPosX()][to.getPosY()].setPiece(capturedPiece);
            }
        }
    }

    /**
     * Simulates a move by executing it on a dummy chessboard.
     *
     * @param chessboard The chessboard.
     * @param square     The move that is to be simulated.
     * @param piece      The piece for which the move should be simulated.
     * @return A chessboard with the simulated move.
     */
    private static Chessboard simulateMove(Chessboard chessboard, Square square, IPiece piece){
        if(piece != null){
            move(chessboard, piece.getSquare().getPosX(), piece.getSquare().getPosY(), square.getPosX(), square.getPosY());
            return chessboard;
        } else {
            return chessboard;
        }
    }

    /**
     * Checks if a king of a given player can be captured be an enemy piece.
     *
     * @param chessboard The chessboard.
     * @param player     The player to be examined.
     * @return True if the player's king can be captured and false if not.
     */
    public static boolean isCheck(Chessboard chessboard, Player player){
        Square kingPosition = getKingPositionForPlayer(chessboard, player);
        ArrayList<Square> enemyMoves = getAllEnemyMoves(chessboard, player);

        for(Square square : enemyMoves){
            if(square.getPosX() == kingPosition.getPosX() && square.getPosY() == kingPosition.getPosY()){
                return true;
            }
        }

        return false;
    }

    /**
     * Get all the moves that the opponents can perform.
     *
     * @param chessboard The chessboard.
     * @param player     The player for whom the opponent's moves are to be calculated.
     * @return A list of all opponent moves.
     */
    private static ArrayList<Square> getAllEnemyMoves(Chessboard chessboard, Player player){
        ArrayList<Square> enemyMoves = new ArrayList<>();

        for(int i = 0; i < chessboard.getSquares().length; i++){
            for(int j = 0; j < chessboard.getSquares()[0].length; j++){
                Square square = chessboard.getSquares()[i][j];

                if(square != null && square.hasPiece()){
                    IPiece piece = square.getPiece();

                    if(piece.getPlayer().getId() != player.getId()){
                        for(Square enemySquare : square.getPiece().getAllowedMoves(chessboard)){
                            enemyMoves.add(enemySquare);
                        }
                    }
                }
            }
        }

        return enemyMoves;
    }

    /**
     * Get the position of the king for a given player.
     *
     * @param chessboard The chessboard.
     * @param player     The player for whom the position of the king is to be determined.
     * @return The field where the king of the player is standing.
     */
    private static Square getKingPositionForPlayer(Chessboard chessboard, Player player){
        for(int i = 0; i < chessboard.getSquares().length; i++){
            for(int j = 0; j < chessboard.getSquares()[0].length; j++){
                Square square = chessboard.getSquares()[i][j];

                if(square != null && square.hasPiece()){
                    IPiece piece = square.getPiece();

                    if(piece.getPlayer().getId() == player.getId() && piece.getType() == PieceType.KING){
                        return square;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Check if a player can perform minimum one valid move.
     *
     * @param chessboard The chessboard.
     * @param player The player who should be checked.
     * @return True if the player has minimum one valid move and false if not.
     */
    public static boolean hasPlayerValidMoves(Chessboard chessboard, Player player){
        for(int i = 0; i < chessboard.getSquares().length; i++){
            for(int j = 0; j < chessboard.getSquares()[0].length; j++){
                Square square = chessboard.getSquares()[i][j];

                if(square != null && square.hasPiece()){
                    IPiece piece = square.getPiece();

                    if(piece.getPlayer().getName() == player.getName() && getValidMovesForPiece(chessboard, piece, player).size() > 0){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Get all players whose kings can be captured by a given player.
     *
     * @param chessboard The chessboard.
     * @param player The given player.
     * @return A list of players whose kings can be captured by the player.
     */
    public static ArrayList<Player> getCaptureKingPlayers(Chessboard chessboard, Player player){
        ArrayList<IPiece> enemyKings = new ArrayList<>();
        ArrayList<Square> playerMoves = new ArrayList<>();

        for(int i = 0; i < chessboard.getSquares().length; i++){
            for(int j = 0; j < chessboard.getSquares()[0].length; j++){
                Square square = chessboard.getSquares()[i][j];

                if(square != null && square.hasPiece()){
                    IPiece piece = square.getPiece();

                    if(piece.getPlayer().getColor() == player.getColor()){

                        for(Square move : getValidMovesForPiece(chessboard, piece, player)){
                            playerMoves.add(move);
                        }
                    } else {
                        if(piece.getPlayer().getColor() != player.getColor() && piece.getType() == PieceType.KING){
                            enemyKings.add(piece);
                        }
                    }
                }
            }
        }

        ArrayList<Player> capturedPlayers = new ArrayList<>();
        for(Square square : playerMoves){
            for(IPiece piece : enemyKings){
                if(square.getPosX() == piece.getSquare().getPosX() && square.getPosY() == piece.getSquare().getPosY()){
                    capturedPlayers.add(piece.getPlayer());
                }
            }
        }

        return capturedPlayers;
    }
}
