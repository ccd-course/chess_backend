package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.constants.PieceType;

import java.util.Objects;

/**
 * Represents a concrete move of a piece with getters and setters for its fields.
 */
public class Move {

    protected Square from = null;
    protected Square to = null;
    protected Piece movedPiece = null;
    protected Piece takenPiece = null;
    protected Piece promotedTo = null;
    protected boolean wasEnPassant = false;
    protected Moves.castling castlingMove = Moves.castling.none;
    protected boolean wasPawnTwoFieldsMove = false;

    public Move(Square from, Square to, Piece movedPiece, Piece takenPiece, Moves.castling castlingMove, boolean wasEnPassant, Piece promotedPiece) {
        this.from = from;
        this.to = to;

        this.movedPiece = movedPiece;
        this.takenPiece = takenPiece;

        this.castlingMove = castlingMove;
        this.wasEnPassant = wasEnPassant;

        if (movedPiece.getType().equals(PieceType.PAWN) && Math.abs(to.getPosY() - from.getPosY()) == 2) {
            this.wasPawnTwoFieldsMove = true;
        }
        // TODO: Implement promotion
//        else if (movedPiece.getType().equals(PieceType.PAWN) && to.pozY == Chessboard.bottom || to.pozY == Chessboard.top && promotedPiece != null)
//        {
//            this.promotedTo = promotedPiece;
//        }
    }

    public Square getFrom() {
        return this.from;
    }

    public Square getTo() {
        return this.to;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public Piece getTakenPiece() {
        return this.takenPiece;
    }

    public boolean wasEnPassant() {
        return this.wasEnPassant;
    }

    public boolean wasPawnTwoFieldsMove() {
        return this.wasPawnTwoFieldsMove;
    }

    public Moves.castling getCastlingMove() {
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
        return Objects.hash(
                getFrom(),
                getTo(),
                getMovedPiece(),
                getTakenPiece(),
                promotedTo,
                wasEnPassant,
                getCastlingMove(),
                wasPawnTwoFieldsMove);
    }

    public Position getToPos() {
        return new Position(getTo().getPosX(), getTo().getPosY());
    }

    public Position getFromPos() {
        return new Position(getFrom().getPosX(), getFrom().getPosY());
    }
}
