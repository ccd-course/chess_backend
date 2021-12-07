package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;

public class MoveForward {

    public MoveForward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     *
     * @param game   Game context
     * @param attack Allow moves to occupied fields (pawn may not attack straight forward)
     * @param jump   Allow moves that pass occupied fields (knight)
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful) {
        return forward(game, fromSquare, attack, jump, peaceful, -1);
    }

    public static Set<Move> forward(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             (limit == -1 && steps < ChessboardService.getMaxY(chessboard.getSquares()))
                     || (steps < limit); steps++) {

            toPosition = toPosition.forward(chessboard);
            Square toSquare = ChessboardService.getSquare(chessboard, toPosition);

            Piece takenPiece = null;
            // TODO: Implement castling, enPassant and piece promotion
            if (toSquare.getPiece() != null) {
                if (attack && toSquare.getPiece().getColor() != fromSquare.getPiece().getColor()) {
                    takenPiece = toSquare.getPiece();
                } else if (jump) {
                    continue;
                } else {
                    break;
                }
            }
            if (peaceful) {
                allowedMoves.add(
                        new Move(
                                fromSquare,
                                toSquare,
                                fromSquare.getPiece(),
                                takenPiece,
                                null,
                                false,
                                null
                        ));
            }
        }
        return allowedMoves;
    }
}
