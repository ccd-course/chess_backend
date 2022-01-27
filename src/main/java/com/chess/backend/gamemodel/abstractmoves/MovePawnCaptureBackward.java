package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a pawn capture move backward. (→ anti-clockwise pawn attack)
 */
public class MovePawnCaptureBackward {

    public MovePawnCaptureBackward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal (backward), one-step
     *
     * @param chessboard The chessboard.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Chessboard chessboard, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(MoveDiagonal.diagonal(chessboard, piece, attack, jump, peaceful, 1, Position.Direction.DIAGONAL_BL));
        allowedMoves.addAll(MoveDiagonal.diagonal(chessboard, piece, attack, jump, peaceful, 1, Position.Direction.DIAGONAL_BR));
        return allowedMoves;
    }

}
