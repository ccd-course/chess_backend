package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;

public class MoveLeft {

    public MoveLeft() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     *
     * @param game   Game context
     * @param attack Allow moves to occupied fields (pawn may not attack straight forward)
     * @param jump   Allow moves that pass occupied fields (knight)
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump) {
        return left(game, fromSquare, attack, jump, -1);
    }

    public static Set<Move> left(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.left(chessboard) != null
                     && (limit == -1 || steps < limit ); steps++) {

            toPosition = toPosition.left(chessboard);
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
        return allowedMoves;
    }
}
