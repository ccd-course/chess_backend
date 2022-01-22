package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.domain.models.IBoard;
import com.chess.backend.domain.models.IPiece;
import com.chess.backend.gamemodel.*;
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
 * Represents the implementation of a diagonal move.
 */
public class Shoot {

    public Shoot() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal, no limit
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(ChessGame game, Square fromSquare, boolean attack, boolean jump, boolean peaceful) {
        return shoot(game, fromSquare, attack, jump, peaceful, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal (every direction), limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> shoot(ChessGame game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        for (int neighbor :
                getNeighborPos(game.getChessboard(), fromSquare.getPos())) {

            switch (neighbor) {
                /*
                    N: Neighbor
                    C: Cannon
                    D: Shooting Direction
                    N # #749
                    # C #
                    # # D
                */
                case 0 -> allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BR));

                /*
                    # N #
                    # C #
                    # D #
                */
                case 1 -> allowedMoves.addAll(backward(game, fromSquare, attack, jump, peaceful, -1));
                case 2 -> allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BL));
                case 3 -> allowedMoves.addAll(left(game, fromSquare, attack, jump, peaceful, -1));
                case 4 -> allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FL));
                case 5 -> allowedMoves.addAll(forward(game, fromSquare, attack, jump, peaceful, -1));
                case 6 -> allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FR));
                case 7 -> allowedMoves.addAll(right(game, fromSquare, attack, jump, peaceful, -1));
            }
        }

        for (Move move :
                allowedMoves) {
            move.setTo(move.getFrom());
        }
        return allowedMoves;
    }

    public static ArrayList<Integer> getNeighborPos(Chessboard chessboard, Position fromPos){
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
            IPiece piece = ChessboardService.getPieceByPosition(chessboard, position);
            if(piece != null){
                playerSet.add(piece.getPlayer());
                neighbors.add(positionsList.indexOf(position));
                continue;
            }
        }
        if (playerSet.size() < 2){
            return neighbors;
        } else{
            return new ArrayList<Integer>();
        }
    }

}
