package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a move backward.
 *
 */
public class MoveBackward {

    public MoveBackward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Backward, no limit
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful) {
        return backward(game, fromSquare, attack, jump, peaceful, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Backward, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> backward(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             (limit == -1 && steps < ChessboardService.getMaxY(chessboard.getSquares()))
                     || (steps < limit); steps++) {

            toPosition = toPosition.backward(chessboard);
            Square toSquare = ChessboardService.getSquare(chessboard, toPosition);

            Piece takenPiece = null;
            // TODO: Implement castling, enPassant and piece promotion
            if (toSquare.getPiece() != null) {
                if (attack && toSquare.getPiece().getColor() != fromSquare.getPiece().getColor()) {
                    takenPiece = toSquare.getPiece();
                } else if (jump) {
                    continue;
                } else {
                    break;
                }
            }
            if (peaceful) {
                allowedMoves.add(
                        new Move(
                                fromSquare,
                                toSquare,
                                fromSquare.getPiece(),
                                takenPiece,
                                null,
                                false,
                                null
                        ));
            }
        }
        return allowedMoves;
    }
}
