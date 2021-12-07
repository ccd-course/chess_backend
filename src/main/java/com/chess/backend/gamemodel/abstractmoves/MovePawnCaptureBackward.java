package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Square;

import java.util.HashSet;
import java.util.Set;

public class MovePawnCaptureBackward {

    public MovePawnCaptureBackward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     *
     * @param game   Game context
     * @param attack Allow moves to occupied fields (pawn may not attack straight forward)
     * @param jump   Allow moves that pass occupied fields (knight)
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump, boolean pieceful) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(MoveDiagonal.diagonalBL(game, fromSquare, attack, jump, pieceful, 1));
        allowedMoves.addAll(MoveDiagonal.diagonalBR(game, fromSquare, attack, jump, pieceful, 1));
        return allowedMoves;
    }

}
