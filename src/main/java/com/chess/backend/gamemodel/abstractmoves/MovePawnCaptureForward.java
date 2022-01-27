package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Position;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.gamemodel.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a pawn capture move forward. (→ clockwise pawn attack)
 */
public class MovePawnCaptureForward {

    public MovePawnCaptureForward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal (forward), one-step
     *
     * @param game       The game context.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(ChessGame game, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(MoveDiagonal.diagonal(game, piece, attack, jump, peaceful, 1, Position.Direction.DIAGONAL_FL));
        allowedMoves.addAll(MoveDiagonal.diagonal(game, piece, attack, jump, peaceful, 1, Position.Direction.DIAGONAL_FR));
        return allowedMoves;
    }

}
