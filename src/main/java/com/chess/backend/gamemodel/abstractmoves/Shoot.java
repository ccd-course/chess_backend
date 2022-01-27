package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.domain.models.IBoard;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.services.ChessGameService;
import com.chess.backend.services.ChessboardService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.chess.backend.gamemodel.abstractmoves.MoveBackward.backward;
import static com.chess.backend.gamemodel.abstractmoves.MoveDiagonal.diagonal;
import static com.chess.backend.gamemodel.abstractmoves.MoveForward.forward;
import static com.chess.backend.gamemodel.abstractmoves.MoveLeft.left;
import static com.chess.backend.gamemodel.abstractmoves.MoveRight.right;


/**
 * Represents the implementation of a cannon shoot.
 */
public class Shoot {

    public Shoot() {
    }

    /**
     * Generate concrete possible shoots from a given piece and game context.
     * Direction: Depends on neighbors, no limit
     *
     * @param game       The game context.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param peaceful   Whether peaceful moves are allowed (moves to an unoccupied square).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(ChessGame game, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        return shoot(game, piece, attack, jump, peaceful, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Depends on neighbors, limit can be set
     *
     * @param game       The game context.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param peaceful   Whether peaceful moves are allowed (moves to an unoccupied square).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> shoot(ChessGame game, Piece piece, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position fromPosition = new Position(piece.getPosX(), piece.getPosY());
        Square fromSquare = ChessboardService.getSquare(chessboard, fromPosition);

        for (int neighbor :
                getNeighborPos(game, fromSquare.getPos())) {

            switch (neighbor) {
                /*
                    N: Neighbor
                    C: Cannon
                    D: Shooting Direction
                    N # #
                    # C #
                    # # D
                */
                case 0 -> allowedMoves.addAll(diagonal(game, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BR));

                /*
                    # N #
                    # C #
                    # D #
                */
                case 1 -> allowedMoves.addAll(backward(game, piece, attack, jump, peaceful, -1));
                /*
                    # # N
                    # C #
                    D # #
                */
                case 2 -> allowedMoves.addAll(diagonal(game, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BL));
                /*
                    # # #
                    D C N
                    # # #
                */
                case 3 -> allowedMoves.addAll(left(game, piece, attack, jump, peaceful, -1));
                /*
                    D # #
                    # C #
                    # # N
                */
                case 4 -> allowedMoves.addAll(diagonal(game, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FL));
                /*
                    # D #
                    # C #
                    # N #
                */
                case 5 -> allowedMoves.addAll(forward(game, piece, attack, jump, peaceful, -1));
                /*
                    # # D
                    # C #
                    N # #
                */
                case 6 -> allowedMoves.addAll(diagonal(game, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FR));
                /*
                    # # #
                    N C D
                    # # #
                */
                case 7 -> allowedMoves.addAll(right(game, piece, attack, jump, peaceful, -1));
                default -> {}
            }
        }

        for (Move move :
                allowedMoves) {
            move.setTo(move.getFrom());
        }
        return allowedMoves;
    }

    public static ArrayList<Integer> getNeighborPos(ChessGame game, Position fromPos){
        ArrayList<Position> positionsList = new ArrayList<>();
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.DIAGONAL_FL));
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.FORWARD));
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.DIAGONAL_FR));
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.RIGHT));
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.DIAGONAL_BR));
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.BACKWARD));
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.DIAGONAL_BL));
        positionsList.add(fromPos.getPosFromDir(game.getChessboard(), Position.Direction.LEFT));
        
        Set<Player> playerSet = new HashSet<>();
        ArrayList<Integer> neighbors = new ArrayList<>();
        for (Position position :
                positionsList) {
            Piece piece = ChessboardService.getPieceByPosition(game.getChessboard(), position);
            if(piece != null){
                playerSet.add(piece.getPlayer());
                neighbors.add(positionsList.indexOf(position));
            }
        }
        if (playerSet.size() < 2 && playerSet.contains(game.getActivePlayer())){
            return neighbors;
        } else{
            return new ArrayList<Integer>();
        }
    }

}
