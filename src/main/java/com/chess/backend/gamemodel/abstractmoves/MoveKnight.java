package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.domain.models.IPiece;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the implementation of a knight move (every direction).
 */
public class MoveKnight {

    public MoveKnight() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Knight-like, one step
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param peaceful   Whether the piece may move to an empty field.
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(ChessGame game, IPiece piece, boolean attack, boolean jump, boolean peaceful) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.FORWARD, Position.Direction.LEFT));
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.FORWARD, Position.Direction.RIGHT));
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.BACKWARD, Position.Direction.LEFT));
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.BACKWARD, Position.Direction.RIGHT));
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.LEFT, Position.Direction.FORWARD));
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.LEFT, Position.Direction.BACKWARD));
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.RIGHT, Position.Direction.FORWARD));
        allowedMoves.addAll(knight(game, piece, attack, jump, peaceful, 3, Position.Direction.RIGHT, Position.Direction.BACKWARD));

        return allowedMoves;
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Knight-Forward-Right, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @param peaceful   Whether the piece may move to an empty field.
     * @return HashSet of concrete moves
     */
    // TODO: Implement castling, enPassant and piece promotion
    public static Set<Move> knight(ChessGame game, IPiece piece, boolean attack, boolean jump, boolean peaceful,
                                   int limit, Position.Direction direction1, Position.Direction direction2) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position fromPosition = new Position(piece.getPosX(), piece.getPosY());
        Square fromSquare = ChessboardService.getSquare(chessboard, fromPosition);

        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0; steps < limit; steps++) {

            if (steps < limit - 1) {
                toPosition = toPosition.getPosFromDir(chessboard, direction1);
            } else {
                toPosition = toPosition.getPosFromDir(chessboard, direction2);
            }
            if (toPosition == null) break;
            Square toSquare = ChessboardService.getSquare(chessboard, toPosition);
            IPiece takenPiece = null;

            if (toSquare.getPiece() != null) {
                if (attack && toSquare.getPiece().getColor() != fromSquare.getPiece().getColor() && steps == limit - 1) {
                    takenPiece = toSquare.getPiece();
                    allowedMoves.add(
                            new Move(fromSquare, toSquare,
                                    fromSquare.getPiece(), takenPiece,
                                    null, false, null
                            ));
                    break;
                } else if (jump) {
                    continue;
                } else {
                    break;
                }
            } else if (peaceful && steps == limit - 1) {
                allowedMoves.add(
                        new Move(fromSquare, toSquare,
                                fromSquare.getPiece(), takenPiece,
                                null, false, null
                        ));
            }
        }
        return allowedMoves;
    }

}
