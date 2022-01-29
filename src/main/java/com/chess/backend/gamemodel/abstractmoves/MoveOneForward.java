package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Piece;

import java.util.Set;

/**
 * Represents the implementation of a one-step move forward.
 */
public class MoveOneForward {

    public MoveOneForward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Forward, one-step
     *
     * @param chessboard The chessboard.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Chessboard chessboard, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        return MoveForward.forward(chessboard, piece, attack, jump, peaceful, 1);
    }
}
