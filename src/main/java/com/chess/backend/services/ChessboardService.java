package com.chess.backend.services;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
            setPiece(x, figuresFirstColumn, squares, new Piece(PieceType.PAWN, player, true));
            setPiece(x, figuresFirstColumn + 3, squares, new Piece(PieceType.PAWN, player, false));
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
        setPiece(0, figuresFirstColumn + 1, squares, new Piece(PieceType.QUEEN, player, false));
        setPiece(1, figuresFirstColumn + 1, squares, new Piece(PieceType.BISHOP, player, false));
        setPiece(2, figuresFirstColumn + 1, squares, new Piece(PieceType.KNIGHT, player, false));
        setPiece(3, figuresFirstColumn + 1, squares, new Piece(PieceType.ROOK, player, false));
        setPiece(4, figuresFirstColumn + 1, squares, new Piece(PieceType.FERZ, player, false));

        // clockwise
        setPiece(0, figuresFirstColumn + 2, squares, new Piece(PieceType.KING, player, true));
        setPiece(1, figuresFirstColumn + 2, squares, new Piece(PieceType.BISHOP, player, true));
        setPiece(2, figuresFirstColumn + 2, squares, new Piece(PieceType.KNIGHT, player, true));
        setPiece(3, figuresFirstColumn + 2, squares, new Piece(PieceType.ROOK, player, true));
        setPiece(3, figuresFirstColumn + 2, squares, new Piece(PieceType.FERZ,player, true));

        //cannon
        setPiece(2, figuresFirstColumn + 6, squares, new Piece(PieceType.CANNON, player, true));
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
     * private function that takes two positions, ie x,y and set piece in Square[x][y]
     *
     * @param posX    position x
     * @param posY    position y
     * @param squares 2D array of Square object
     */
    public static void removePiece(int posX, int posY, ArrayList<ArrayList<Square>> squares) {

        Square square = squares.get(posX).get(posY);

        square.removePiece();
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

        chessboard.getSquares().get(move.getTo().getPosX()).get(move.getTo().getPosY()).setPiece(piece);
//                [move.getTo().getPosX()][move.getTo().getPosY()]
//                .setPiece(piece);
        chessboard.getSquares().get(move.getFrom().getPosX()).get(move.getFrom().getPosY()).removePiece();
//                [move.getFrom().getPosX()][move.getFrom().getPosY()]
//                .removePiece();

        if(piece.getType() == PieceType.PAWN){
            rankUpPawn(piece, move.getFrom().getPosY(), move.getTo().getPosY(), getChessboardLength(chessboard));

            if(checkPawnPromotion(piece)){
                promotePawn(chessboard, piece);
            }
        }
    }

    public static Chessboard move(Chessboard chessboard, int fromX, int fromY, int toX, int toY) {
        Piece piece = chessboard.getSquares().get(fromX).get(fromY).getPiece();

        chessboard.getSquares().get(toX).get(toY).setPiece(piece);
        chessboard.getSquares().get(fromX).get(fromY).removePiece();
        if (piece.getType() != PieceType.CANNON) {
            chessboard.getSquares()
                    .get(toX).get(toY).setPiece(piece);
            chessboard.getSquares().get(fromX).get(fromY).removePiece();
        } else {
            chessboard.getSquares().get(toX).get(toY).removePiece();
        }
        if(piece.getType() == PieceType.PAWN){
            rankUpPawn(piece, fromY, toY, getChessboardLength(chessboard));

            if(checkPawnPromotion(piece)){
                promotePawn(chessboard, piece);
            }
        }
        return chessboard;

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

    /**
     * Ranking up a pawn after a move.
     *
     * @param pawn The pawn to be ranked up.
     * @param posFrom The position the pawn was moved from.
     * @param posTo The position the pawn was moved to.
     * @param chessboardLength The length of the chessboard (y-axis).
     */
    private static void rankUpPawn(Piece pawn, int posFrom, int posTo, int chessboardLength){
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
    private static void rankDownPawn(Piece pawn, int posFrom, int posTo, int chessboardLength){
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
        return chessboard.getSquares().get(0).size();
    }

    /**
     * Check if a pawn has reached the maximum rank to be promoted.
     *
     * @param pawn The pawn that has to be checked.
     * @return True if the pawn has reached the maximum rank and false if not.
     */
    private static boolean checkPawnPromotion(Piece pawn){
        return pawn.getRank() == 9;
    }

    /**
     * Promote a piece to a queen.
     *
     * @param chessboard The chessboard.
     * @param piece The piece that has to be promoted.
     */
    private static void promotePawn(Chessboard chessboard, Piece piece) {
        chessboard.getSquares().get(piece.getPosX()).get(piece.getPosY()).removePiece();
        setPiece(piece.getPosX(), piece.getPosY(), chessboard.getSquares(), new Piece(PieceType.QUEEN, piece.getPlayer(), piece.isClockwise()));
    }


    /**
     * Sets one common player for all pieces of a given pieceType (e.g. cannon)
     * @param chessboard The chessboard context
     * @param pieceType PieceType on which the common player should be applied
     * @param player Player that should be applied on all pieces of given pieceType.
     */
    public static void setCommonPiecePlayer(Chessboard chessboard, PieceType pieceType, Player player){
        for (Square square :
                searchSquaresByPiece(chessboard.getSquares(), pieceType, null, null)) {
            square.getPiece().setPlayer(player);
        }
    }

    /**
     * Gets all valid moves for a piece. Valid means that the move is possible for the piece and doesn't end up in a check for the piece owner.
     *
     * @param chessboard The chessboard.
     * @param piece      The piece for which the moves are to be determined.
     * @return A list of valid moves.
     */
    public static ArrayList<Square> getValidMovesForPiece(Chessboard chessboard, Piece piece, Player player){
        ArrayList<Square> possibleMoves = piece.getAllowedMoves(chessboard);
        ArrayList<Square> validMoves = new ArrayList<>();

        for(Square square : possibleMoves){
            Piece capturedPiece = square.getPiece();
            Square fromSquare = ChessboardService.getSquare(chessboard, piece.getPosition());

            if(!isCheck(simulateMove(chessboard, square, piece), player)){
                validMoves.add(square);
            }

            revertMove(chessboard, capturedPiece, piece, square, fromSquare);
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
    private static void revertMove(Chessboard chessboard, Piece capturedPiece, Piece piece, Square to, Square from){
        if(piece.getType() == PieceType.CANNON){
            setPiece(to.getPosX(), to.getPosY(), chessboard.getSquares(), capturedPiece);
        } else {
            setPiece(from.getPosX(), from.getPosY(), chessboard.getSquares(), piece);
            removePiece(to.getPosX(), to.getPosY(), chessboard.getSquares());

            //rank down a pawn
            if(piece.getType() == PieceType.PAWN){
                rankDownPawn(piece, from.getPosY(), to.getPosY(), getChessboardLength(chessboard));
            }

            if(capturedPiece != null){
                setPiece(to.getPosX(), to.getPosY(), chessboard.getSquares(), capturedPiece);            }
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
    private static Chessboard simulateMove(Chessboard chessboard, Square square, Piece piece){
        if(piece != null){
            move(chessboard, piece.getPosX(), piece.getPosY(), square.getPosX(), square.getPosY());
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

        //iterate over chessboard
        for(int i = 0; i < chessboard.getSquares().size(); i++){
            for(int j = 0; j < chessboard.getSquares().get(0).size(); j++){
                Square square = chessboard.getSquares().get(i).get(j);

                //check if square has a piece
                if(square != null && square.hasPiece()){
                    Piece piece = square.getPiece();

                    if(!piece.getPlayer().getName().equals(player.getName())){
                        enemyMoves.addAll(square.getPiece().getAllowedMoves(chessboard));
                    }
                }
            }
        }

        enemyMoves.addAll(getAllEnemyCanonMoves(chessboard, player));
        return enemyMoves;
    }

    /**
     * Calculate all the possible moves the opponents can perform with the canons for a given player.
     *
     * @param chessboard The chessboard.
     * @param player The given player.
     * @return A list of all possible canon moves the opponents can perform.
     */
    private static ArrayList<Square> getAllEnemyCanonMoves(Chessboard chessboard, Player player){
        ArrayList<Piece> canons = new ArrayList<>();

        for(int i = 0; i < chessboard.getSquares().size(); i++){
            for(int j = 0; j < chessboard.getSquares().get(0).size(); j++) {
                Square square = chessboard.getSquares().get(i).get(j);

                if(square != null && square.hasPiece() && square.getPiece().getType() == PieceType.CANNON) {
                    canons.add(square.getPiece());
                }
            }
        }

        ArrayList<Player> enemys = getAllEnemyPlayers(chessboard, player);
        ArrayList<Square> canonMoves = new ArrayList<>();

        for(Player enemy : enemys){
            setCommonPiecePlayer(chessboard, PieceType.CANNON, enemy);

            for(Piece canon : canons){
                canonMoves.addAll(canon.getAllowedMoves(chessboard));
            }
        }

        setCommonPiecePlayer(chessboard, PieceType.CANNON, player);
        return canonMoves;
    }

    /**
     * Getting all the opponent players for a given player.
     *
     * @param chessboard The chessboard.
     * @param player The given player.
     * @return A list with all the opponent players.
     */
    private static ArrayList<Player> getAllEnemyPlayers(Chessboard chessboard, Player player){
        ArrayList<Player> enemys = new ArrayList<>();

        for(int i = 0; i < chessboard.getSquares().size(); i++) {
            for (int j = 0; j < chessboard.getSquares().get(0).size(); j++) {
                Square square = chessboard.getSquares().get(i).get(j);

                if(square != null && square.hasPiece()) {
                    Piece piece = square.getPiece();

                    if(!piece.getPlayer().getName().equals(player.getName()) && !enemys.contains(piece.getPlayer())){
                        enemys.add(piece.getPlayer());
                    }
                }
            }
        }

        return enemys;
    }

    /**
     * Get the position of the king for a given player.
     *
     * @param chessboard The chessboard.
     * @param player     The player for whom the position of the king is to be determined.
     * @return The field where the king of the player is standing.
     */
    private static Square getKingPositionForPlayer(Chessboard chessboard, Player player){
        for(int i = 0; i < chessboard.getSquares().size(); i++){
            for(int j = 0; j < chessboard.getSquares().get(0).size(); j++){
                Square square = chessboard.getSquares().get(i).get(j);

                if(square != null && square.hasPiece()){
                    Piece piece = square.getPiece();

                    if(piece.getPlayer().getName().equals(player.getName()) && piece.getType() == PieceType.KING){
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
        for(int i = 0; i < chessboard.getSquares().size(); i++){
            for(int j = 0; j < chessboard.getSquares().get(0).size(); j++){
                Square square = chessboard.getSquares().get(i).get(j);

                if(square != null && square.hasPiece()){
                    Piece piece = square.getPiece();

                    if(piece.getPlayer().getName().equals(player.getName()) && !getValidMovesForPiece(chessboard, piece, player).isEmpty()){
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
        ArrayList<Piece> enemyKings = new ArrayList<>();
        ArrayList<Square> playerMoves = new ArrayList<>();

        for(int i = 0; i < chessboard.getSquares().size(); i++){
            for(int j = 0; j < chessboard.getSquares().get(0).size(); j++){
                Square square = chessboard.getSquares().get(i).get(j);

                if(square != null && square.hasPiece()){
                    Piece piece = square.getPiece();

                    if(piece.getPlayer().getName().equals(player.getName())){

                        playerMoves.addAll(getValidMovesForPiece(chessboard, piece, player));
                    } else {
                        if(piece.getType() == PieceType.KING){
                            enemyKings.add(piece);
                        }
                    }
                }
            }
        }

        ArrayList<Player> capturedPlayers = new ArrayList<>();
        for(Square square : playerMoves){
            for(Piece piece : enemyKings){
                if(square.getPosX() == piece.getPosX() && square.getPosY() == piece.getPosY()){
                    capturedPlayers.add(piece.getPlayer());
                }
            }
        }

        return capturedPlayers;
    }
}
