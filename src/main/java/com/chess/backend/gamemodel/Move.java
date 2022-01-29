package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.constants.Castling;
import com.chess.backend.gamemodel.constants.PieceType;
import lombok.Data;

import java.util.Objects;

/**
 * Represents a concrete move of a piece with getters and setters for its fields.
 */
@Data
public class Move {
    private Square from = null;
    private Square to = null;
    private Square taken = null;
    private Piece movedPiece = null;
    private Piece takenPiece = null;
    private Piece promotedTo = null;
    private boolean wasEnPassant = false;
    private Castling castlingMove = Castling.NONE;
    private boolean wasPawnTwoFieldsMove = false;

    public Move(Square from, Square to, Square taken, Piece movedPiece, Piece takenPiece, Castling castlingMove, boolean wasEnPassant, Piece promotedPiece) {
        this.from = from;
        this.to = to;
        this.taken = taken;

        this.movedPiece = movedPiece;
        this.takenPiece = takenPiece;

        this.castlingMove = castlingMove;
        this.wasEnPassant = wasEnPassant;

        if (movedPiece.getType().equals(PieceType.PAWN) && Math.abs(to.getPosY() - from.getPosY()) == 2) {
            this.wasPawnTwoFieldsMove = true;
        }
        System.out.println(promotedPiece);//avoid static error
        // TODO: Implement promotion
//        else if (movedPiece.getType().equals(PieceType.PAWN) && to.pozY == Chessboard.bottom || to.pozY == Chessboard.top && promotedPiece != null)
//        {
//            this.promotedTo = promotedPiece;
//        }
    }

    public boolean wasEnPassant() {
        return this.wasEnPassant;
    }

    public boolean wasPawnTwoFieldsMove() {
        return this.wasPawnTwoFieldsMove;
    }

    public Castling getCastlingMove() {
        return this.castlingMove;
    }

    public Piece getPromotedPiece() {
        return this.promotedTo;
    }

    @Override
    public String toString() {
        return "\nMove{" +
                "\nfrom=" + from +
                "\n, to=" + to +
                "\n, movedPiece=" + movedPiece +
                "\n, takenPiece=" + takenPiece +
                "\n, promotedTo=" + promotedTo +
                "\n, wasEnPassant=" + wasEnPassant +
                "\n, castlingMove=" + castlingMove +
                "\n, wasPawnTwoFieldsMove=" + wasPawnTwoFieldsMove +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return wasEnPassant == move.wasEnPassant
                && wasPawnTwoFieldsMove == move.wasPawnTwoFieldsMove
                && getFrom().equals(move.getFrom())
                && getTo().equals(move.getTo())
                && getMovedPiece().equals(move.getMovedPiece())
                && Objects.equals(getTakenPiece(), move.getTakenPiece())
                && Objects.equals(promotedTo, move.promotedTo)
                && getCastlingMove() == move.getCastlingMove();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo(), getTaken(), getMovedPiece(), getTakenPiece(), getPromotedTo(), isWasEnPassant(), getCastlingMove(), isWasPawnTwoFieldsMove());
    }

    public Position getToPos() {
        return new Position(getTo().getPosX(), getTo().getPosY());
    }

    public Position getTakenPos() {
        return new Position(getTaken().getPosX(), getTaken().getPosY());
    }

    public Position getFromPos() {
        return new Position(getFrom().getPosX(), getFrom().getPosY());
    }
}
