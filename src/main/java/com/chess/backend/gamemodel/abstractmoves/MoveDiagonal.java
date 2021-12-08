package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.Square;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents the implementation of a diagonal move.
 *
 */
public class MoveDiagonal {

    public MoveDiagonal() {
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal, no limit
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump) {
        return diagonal(game, fromSquare, attack, jump, -1);
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal (every direction), limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> diagonal(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(diagonalBL(game, fromSquare, attack, jump, limit));
        allowedMoves.addAll(diagonalBR(game, fromSquare, attack, jump, limit));
        allowedMoves.addAll(diagonalFL(game, fromSquare, attack, jump, limit));
        allowedMoves.addAll(diagonalFR(game, fromSquare, attack, jump, limit));
        return allowedMoves;
    }

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal backward left, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> diagonalBL(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        int x = fromSquare.getPozX();
        int y = fromSquare.getPozY();

        for (int n = 0; (x + n) < ChessboardService.getMaxX(game.chessboard.getSquares()) &&
                (y + n) < ChessboardService.getMaxX(game.chessboard.getSquares()) &&
                (limit > 0 || limit == -1); n++) {
            if (limit != -1) limit--;

            Square toSquare = game.chessboard.squares[x + n][y + n];
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

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal backward right, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> diagonalBR(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        int x = fromSquare.getPozX();
        int y = fromSquare.getPozY();

        for (int n = 0; (x - n) > 0 &&
                (y + n) < ChessboardService.getMaxX(game.chessboard.getSquares()) &&
                (limit > 0 || limit == -1); n++) {
            if (limit != -1) limit--;

            Square toSquare = game.chessboard.squares[x - n][y + n];
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

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal forward right, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> diagonalFR(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        int x = fromSquare.getPozX();
        int y = fromSquare.getPozY();

        for (int n = 0; (x - n) > 0 &&
                (y - n) > 0 &&
                (limit > 0 || limit == -1); n++) {
            if (limit != -1) limit--;

            Square toSquare = game.chessboard.squares[x - n][y - n];
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

    /**
     * Generate concrete possible moves from a given piece and game context.
     * Direction: Diagonal forward left, limit can be set
     *
     * @param game       The game context.
     * @param fromSquare The originating square.
     * @param attack     Whether the piece may move to an occupied square. This would result in an attack with a captured piece.
     * @param jump       Whether the piece may jump over other pieces (e.g. the knight).
     * @param limit      The maximum of steps.
     * @return HashSet of concrete moves
     */
    public static Set<Move> diagonalFL(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        int x = fromSquare.getPozX();
        int y = fromSquare.getPozY();

        for (int n = 0; (x + n) < ChessboardService.getMaxX(game.chessboard.getSquares()) &&
                (y - n) > 0 &&
                (limit > 0 || limit == -1); n++) {
            if (limit != -1) limit--;

            Square toSquare = game.chessboard.squares[x + n][y - n];
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
