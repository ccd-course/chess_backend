package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;
import com.chess.backend.gamemodel.Piece;
import com.chess.backend.gamemodel.Square;

import java.util.HashSet;
import java.util.Set;

public class MoveBackward {

    public MoveBackward() {}

    /**
     * Generate concrete possible moves from a given piece and game context.
     * @param game Game context
     * @param attack Allow moves to occupied fields (pawn may not attack straight forward)
     * @param jump Allow moves that pass occupied fields (knight)
     * @return HashSet of concrete moves
     */
    public static Set<Move> concretise(Game game, Square fromSquare, boolean attack, boolean jump){
        HashSet<Move> allowedMoves = new HashSet<Move>();

        for (int x = fromSquare.getPozX(); x > 0; x++) {
            Square toSquare = game.chessboard.squares[x][fromSquare.getPozY()];
            Piece takenPiece = null;
            // TODO: Implement castling, enPassant and piece promotion
            if (attack){
                takenPiece = toSquare.piece;
            }
            allowedMoves.add(new Move(fromSquare, toSquare, fromSquare.piece, takenPiece, null, false, null));
        }

        return allowedMoves;
    }

}
