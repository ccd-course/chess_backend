package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.Square;
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
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump) {
        return backward(game, fromSquare, attack, jump, -1);
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
    public static Set<Move> backward(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        for (int y = fromSquare.getPozY(); y < ChessboardService.getMaxX(game.chessboard.getSquares()) && (limit > 0 || limit == -1); y++) {
            if (limit != -1) limit--;

            Square toSquare = game.chessboard.squares[fromSquare.getPozX()][y];
            Piece takenPiece = null;
            // TODO: Implement castling, enPassant and piece promotion
            if (toSquare.piece != null) {
                if (attack) {
                    takenPiece = toSquare.piece;
                } else if (jump) {
                    continue;
                } else {
                    break;
                }
            }
            allowedMoves.add(
                    new Move(
                            fromSquare,
                            toSquare,
                            fromSquare.piece,
                            takenPiece,
                            null,
                            false,
                            null
                    ));
        }
        return allowedMoves;
    }
}
