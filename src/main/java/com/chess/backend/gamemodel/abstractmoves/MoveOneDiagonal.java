package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Square;

import java.util.Set;

/**
 * Represents the implementation of a one-step move diagonal (every direction).
 */
public class MoveOneDiagonal {

    public MoveOneDiagonal() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal (every direction), one-step
     *
     * @param chessboard The chessboard.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Chessboard chessboard, Square fromSquare, boolean attack, boolean jump, boolean pieceful) {
        return MoveDiagonal.diagonal(chessboard, fromSquare, attack, jump, pieceful, 1);
    }

}
