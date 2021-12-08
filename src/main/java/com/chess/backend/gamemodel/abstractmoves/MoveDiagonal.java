package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.services.ChessboardService;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents the implementation of a diagonal move.
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
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful) {
        return diagonal(game, fromSquare, attack, jump, peaceful, -1);
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
    public static Set<Move> diagonal(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(diagonalBL(game, fromSquare, attack, jump, peaceful, limit));
        allowedMoves.addAll(diagonalBR(game, fromSquare, attack, jump, peaceful, limit));
        allowedMoves.addAll(diagonalFL(game, fromSquare, attack, jump, peaceful, limit));
        allowedMoves.addAll(diagonalFR(game, fromSquare, attack, jump, peaceful, limit));
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
    public static Set<Move> diagonalBL(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagBL(chessboard) != null
                     && (limit == -1 || steps < limit); steps++) {

            toPosition = toPosition.diagBL(chessboard);
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
    public static Set<Move> diagonalBR(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagBR(chessboard) != null
                     && (limit == -1 || steps < limit); steps++) {

            toPosition = toPosition.diagBR(chessboard);
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
    public static Set<Move> diagonalFR(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagFR(chessboard) != null
                     && (limit == -1 || steps < limit); steps++) {

            toPosition = toPosition.diagFR(chessboard);
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
    public static Set<Move> diagonalFL(Game game, Square fromSquare, boolean attack, boolean jump, boolean peaceful, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagFL(chessboard) != null
                     && (limit == -1 || steps < limit); steps++) {

            toPosition = toPosition.diagFL(chessboard);
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
