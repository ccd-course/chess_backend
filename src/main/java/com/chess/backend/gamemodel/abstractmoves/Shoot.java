package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.domain.models.IBoard;
import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.services.ChessGameService;
import com.chess.backend.services.ChessboardService;

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
     * @param chessboard The chessboard.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param peaceful   Whether peaceful moves are allowed (moves to an unoccupied square).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Chessboard chessboard, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        return shoot(chessboard, piece, attack, jump, peaceful, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Depends on neighbors, limit can be set
     *
     * @param chessboard The chessboard.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param peaceful   Whether peaceful moves are allowed (moves to an unoccupied square).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> shoot(Chessboard chessboard, Piece piece, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Position fromPosition = new Position(piece.getPosX(), piece.getPosY());
        Square fromSquare = ChessboardService.getSquare(chessboard, fromPosition);

        for (int neighbor :
                getNeighborPos(chessboard, fromSquare.getPos(), piece.getPlayer())) {

            switch (neighbor) {
                /*
                    N: Neighbor
                    C: Cannon
                    D: Shooting Direction
                    N # #
                    # C #
                    # # D
                */
                case 0 -> allowedMoves.addAll(diagonal(chessboard, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BR));

                /*
                    # N #
                    # C #
                    # D #
                */
                case 1 -> allowedMoves.addAll(backward(chessboard, piece, attack, jump, peaceful, -1));
                /*
                    # # N
                    # C #
                    D # #
                */
                case 2 -> allowedMoves.addAll(diagonal(chessboard, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BL));
                /*
                    # # #
                    D C N
                    # # #
                */
                case 3 -> allowedMoves.addAll(left(chessboard, piece, attack, jump, peaceful, -1));
                /*
                    D # #
                    # C #
                    # # N
                */
                case 4 -> allowedMoves.addAll(diagonal(chessboard, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FL));
                /*
                    # D #
                    # C #
                    # N #
                */
                case 5 -> allowedMoves.addAll(forward(chessboard, piece, attack, jump, peaceful, -1));
                /*
                    # # D
                    # C #
                    N # #
                */
                case 6 -> allowedMoves.addAll(diagonal(chessboard, piece, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FR));
                /*
                    # # #
                    N C D
                    # # #
                */
                case 7 -> allowedMoves.addAll(right(chessboard, piece, attack, jump, peaceful, -1));
                default -> {}
            }
        }

        for (Move move :
                allowedMoves) {
            move.setTo(move.getFrom());
        }
        return allowedMoves;
    }

    public static ArrayList<Integer> getNeighborPos(Chessboard chessboard, Position fromPos, Player activePlayer){
        ArrayList<Position> positionsList = new ArrayList<>();
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.DIAGONAL_FL));
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.FORWARD));
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.DIAGONAL_FR));
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.RIGHT));
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.DIAGONAL_BR));
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.BACKWARD));
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.DIAGONAL_BL));
        positionsList.add(fromPos.getPosFromDir(chessboard, Position.Direction.LEFT));
        
        Set<Player> playerSet = new HashSet<>();
        ArrayList<Integer> neighbors = new ArrayList<>();
        for (Position position :
                positionsList) {
            Piece piece = ChessboardService.getPieceByPosition(chessboard, position);
            if(piece != null){
                playerSet.add(piece.getPlayer());
                neighbors.add(positionsList.indexOf(position));
            }
        }
        if (playerSet.size() < 2 && playerSet.contains(activePlayer)){
            return neighbors;
        } else{
            return new ArrayList<Integer>();
        }
    }

}
