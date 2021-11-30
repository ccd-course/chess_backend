package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.Square;

import java.util.HashSet;
import java.util.Set;

public class MoveDiagonal {

    public MoveDiagonal() {
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
        return diagonal(game, fromSquare, attack, jump, -1);
    }

    public static Set<Move> diagonal(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        allowedMoves.addAll(diagonalBL(game, fromSquare, attack, jump, limit));
        allowedMoves.addAll(diagonalBR(game, fromSquare, attack, jump, limit));
        allowedMoves.addAll(diagonalFL(game, fromSquare, attack, jump, limit));
        allowedMoves.addAll(diagonalFR(game, fromSquare, attack, jump, limit));
        return allowedMoves;
    }

    // Diagonal backward left
    public static Set<Move> diagonalBL(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        int x = fromSquare.getPozX();
        int y = fromSquare.getPozY();

        for (int n = 0; (x + n) < game.chessboard.getWidth() &&
                (y + n) < game.chessboard.getHeight() &&
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

    // Diagonal backward right
    public static Set<Move> diagonalBR(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        int x = fromSquare.getPozX();
        int y = fromSquare.getPozY();

        for (int n = 0; (x - n) > 0 &&
                (y + n) < game.chessboard.getHeight() &&
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

    // Diagonal forward right
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

    // Diagonal forward left
    public static Set<Move> diagonalFL(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();

        int x = fromSquare.getPozX();
        int y = fromSquare.getPozY();

        for (int n = 0; (x + n) < game.chessboard.getWidth() &&
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
