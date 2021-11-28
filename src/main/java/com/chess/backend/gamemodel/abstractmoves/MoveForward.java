package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Square;

import java.util.HashSet;
import java.util.Set;

public class MoveForward {

    public MoveForward() {}

    /**
     * Generate concrete possible moves from a given piece and game context.
     * @param game Game context
     * @param attack Allow moves to occupied fields (pawn may not attack straight forward)
     * @param jump Allow moves that pass occupied fields (knight)
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square square, boolean attack, boolean jump){

        return new HashSet<>();
    }

}
