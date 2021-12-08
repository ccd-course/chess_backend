package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Square;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a knight move (every direction).
 */
public class MoveKnight {

    public MoveKnight() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     *
     * @param game       Game context
     * @param attack     Allow moves to occupied fields (pawn may not attack straight forward)
     * @param jump       Allow moves that pass occupied fields (knight)
     *                   Direction: Knight-like, one step
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump) {

        return new HashSet<>();
    }

}
