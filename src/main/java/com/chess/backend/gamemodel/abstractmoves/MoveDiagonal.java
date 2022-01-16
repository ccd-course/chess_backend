package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.domain.models.IPiece;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents the implementation of a diagonal move.
 */
public class MoveDiagonal {

    public MoveDiagonal() {
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
        return diagonal(game, fromSquare, attack, jump, peaceful, -1);
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
    public static Set<Move> diagonal(ChessGame game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BL));
        allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_BR));
        allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FL));
        allowedMoves.addAll(diagonal(game, fromSquare, attack, jump, peaceful, limit, Position.Direction.DIAGONAL_FR));
        return allowedMoves;
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal backward left, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    // TODO: Implement castling, enPassant and piece promotion
    public static Set<Move> diagonal(ChessGame game, Square fromSquare, boolean attack, boolean jump, boolean peaceful,
                                     int limit, Position.Direction direction) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.getPosFromDir(chessboard, direction) != null
                     && (limit == -1 || steps < limit); steps++) {

            toPosition = toPosition.getPosFromDir(chessboard, direction);
            Square toSquare = ChessboardService.getSquare(chessboard, toPosition);
            IPiece takenPiece = null;

            if (toSquare.getPiece() != null) {
                if (attack && toSquare.getPiece().getColor() != fromSquare.getPiece().getColor()) {
                    takenPiece = toSquare.getPiece();
                    allowedMoves.add(
                            new Move(fromSquare, toSquare,
                                    fromSquare.getPiece(), takenPiece,
                                    null, false, null
                            ));
                    break;
                } else if (jump) {
                    continue;
                } else {
                    break;
                }
            } else if (peaceful) {
                allowedMoves.add(
                        new Move(fromSquare, toSquare,
                                fromSquare.getPiece(), takenPiece,
                                null, false, null
                        ));
            }
        }
        return allowedMoves;
    }
}
