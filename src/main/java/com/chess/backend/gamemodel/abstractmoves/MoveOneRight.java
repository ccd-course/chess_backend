package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.pieces.Piece;

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
     * @param game       The game context.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(ChessGame game, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        return MoveRight.right(game, piece, attack, jump, peaceful, 1);
    }

}
