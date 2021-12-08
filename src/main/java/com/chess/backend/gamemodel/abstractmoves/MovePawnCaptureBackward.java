package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Square;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a pawn capture move backward. (→ anti-clockwise pawn attack)
 *
 */
public class MovePawnCaptureBackward {

    public MovePawnCaptureBackward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal (backward), one-step
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump, boolean pieceful) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(MoveDiagonal.diagonalBL(game, fromSquare, attack, jump, pieceful, 1));
        allowedMoves.addAll(MoveDiagonal.diagonalBR(game, fromSquare, attack, jump, pieceful, 1));
        return allowedMoves;
    }

}
