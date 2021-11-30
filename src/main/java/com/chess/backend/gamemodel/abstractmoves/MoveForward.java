package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Piece;
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
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump){
        return forward(game, fromSquare, attack, jump, -1);
    }

    public static Set<Move> forward(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        for (int y = fromSquare.getPozY(); y > 0 && (limit > 0 || limit == -1); y--) {
            if (limit != -1) limit--;

            Square toSquare = game.chessboard.squares[fromSquare.getPozX()][y];
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
