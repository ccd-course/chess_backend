package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.pieces.Piece;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a move to the right.
 */
public class MoveRight {

    public MoveRight() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Right, no limit
     *
     * @param chessboard The chessboard.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Chessboard chessboard, Piece piece, boolean attack, boolean jump, boolean peaceful) {
        return right(chessboard, piece, attack, jump, peaceful, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Right, limit can be set
     *
     * @param chessboard The chessboard.
     * @param piece The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    // TODO: Implement castling, enPassant and piece promotion
    public static Set<Move> right(Chessboard chessboard, Piece piece, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Position fromPosition = new Position(piece.getPosX(), piece.getPosY());
        Square fromSquare = ChessboardService.getSquare(chessboard, fromPosition);

        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.right(chessboard) != null
                     && (limit == -1 || steps < limit); steps++) {

            toPosition = toPosition.right(chessboard);
            Square toSquare = ChessboardService.getSquare(chessboard, toPosition);
            Piece takenPiece = null;

            if (toSquare.getPiece() != null) {
                if (attack && toSquare.getPiece().getColor() != fromSquare.getPiece().getColor()) {
                    takenPiece = toSquare.getPiece();
                    allowedMoves.add(
                            new Move(fromSquare, toSquare, toSquare,
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
