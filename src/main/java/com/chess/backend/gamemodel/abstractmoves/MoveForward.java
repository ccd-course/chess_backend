package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a move forward.
 */
public class MoveForward {

    public MoveForward() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     *
     * @param game       Game context
     * @param attack     Allow moves to occupied fields (pawn may not attack straight forward)
     * @param jump       Allow moves that pass occupied fields (knight)
     *                   Direction: Forward, no limit
     * @param game       The game context.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(ChessGame game, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        return forward(game, piece, attack, jump, peaceful, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Forward, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    // TODO: Implement castling, enPassant and piece promotion
    public static Set<Move> forward(ChessGame game, Piece piece, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();

        Position fromPosition = new Position(piece.getPosX(), piece.getPosY());
        Square fromSquare = ChessboardService.getSquare(chessboard, fromPosition);

        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             (limit == -1 && steps < ChessboardService.getMaxY(chessboard.getSquares()))
                     || (steps < limit); steps++) {

            toPosition = toPosition.forward(chessboard);
            Square toSquare = ChessboardService.getSquare(chessboard, toPosition);
            Piece takenPiece = null;

            if (toSquare.getPiece() != null) {
                if (attack && toSquare.getPiece().getColor() != fromSquare.getPiece().getColor()) {
                    takenPiece = toSquare.getPiece();
                    allowedMoves.add(
                            new Move(fromSquare, toSquare, toSquare ,
                                    fromSquare.getPiece(),
                                    takenPiece, null, false,
                                    null));
                    break;
                } else if (jump) {
                    continue;
                } else {
                    break;
                }
            } else if (peaceful) {
                allowedMoves.add(
                        new Move(fromSquare, toSquare, null,
                                fromSquare.getPiece(),
                                null, null, false,
                                null));
            }
        }
        return allowedMoves;
    }
}
