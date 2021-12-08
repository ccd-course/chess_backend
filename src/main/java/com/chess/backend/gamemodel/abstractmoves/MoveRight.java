package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.Square;

import java.util.HashSet;
import java.util.Set;

public class MoveRight {

    /**
     * Represents the implementation of a move to the right.
     *
     */
    public MoveRight() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Right, no limit
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump) {
        return right(game, fromSquare, attack, jump, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Right, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> right(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        for (int x = fromSquare.getPozX(); x > 0 && (limit > 0 || limit == -1); x--) {
            if (limit != -1) limit--;

            Square toSquare = game.chessboard.squares[x][fromSquare.getPozY()];
            Piece takenPiece = null;
            // TODO: Implement castling, enPassant and piece promotion
            if (toSquare.piece != null) {
                if (attack) {
                    takenPiece = toSquare.piece;
                } else if (jump) {
                    continue;
                } else {
                    break;
                }
            }
            allowedMoves.add(
                    new Move(
                            fromSquare,
                            toSquare,
                            fromSquare.piece,
                            takenPiece,
                            null,
                            false,
                            null
                    ));
        }
        return allowedMoves;
    }

}
