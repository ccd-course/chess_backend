package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.*;
import com.chess.backend.services.ChessboardService;

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
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagBL(chessboard) != null
                     && (limit == -1 || steps < limit ); steps++) {

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

    // Diagonal backward right
    public static Set<Move> diagonalBR(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagBR(chessboard) != null
                     && (limit == -1 || steps < limit ); steps++) {

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

    // Diagonal forward right
    public static Set<Move> diagonalFR(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagFR(chessboard) != null
                     && (limit == -1 || steps < limit ); steps++) {

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

    // Diagonal forward left
    public static Set<Move> diagonalFL(Game game, Square fromSquare, boolean attack, boolean jump, int limit) {
        HashSet<Move> allowedMoves = new HashSet<Move>();
        Chessboard chessboard = game.getChessboard();
        Position toPosition = new Position(fromSquare.getPosX(), fromSquare.getPosY());

        for (int steps = 0;
             toPosition.diagFL(chessboard) != null
                     && (limit == -1 || steps < limit ); steps++) {

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
