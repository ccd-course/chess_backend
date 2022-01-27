package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Square;

import java.util.Set;

/**
 * Represents the implementation of a one-step move to the right.
 */
public class MoveOneRight {

    public MoveOneRight() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Right, one-step
     *
     * @param chessboard The chessboard.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Chessboard chessboard, Square fromSquare, boolean attack, boolean jump, boolean peaceful) {
        return MoveRight.right(chessboard, fromSquare, attack, jump, peaceful, 1);
    }

}
