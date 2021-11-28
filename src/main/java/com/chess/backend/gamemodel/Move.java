package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.constants.PieceType;

public class Move {

    protected Square from = null;
    protected Square to = null;
    protected Piece movedPiece = null;
    protected Piece takenPiece = null;
    protected Piece promotedTo = null;
    protected boolean wasEnPassant = false;
    protected Moves.castling castlingMove = Moves.castling.none;
    protected boolean wasPawnTwoFieldsMove = false;

    Move(Square from, Square to, Piece movedPiece, Piece takenPiece, Moves.castling castlingMove, boolean wasEnPassant, Piece promotedPiece)
    {
        this.from = from;
        this.to = to;

        this.movedPiece = movedPiece;
        this.takenPiece = takenPiece;

        this.castlingMove = castlingMove;
        this.wasEnPassant = wasEnPassant;

        if (movedPiece.getType().equals(PieceType.PAWN) && Math.abs(to.pozY - from.pozY) == 2)
        {
            this.wasPawnTwoFieldsMove = true;
        }
        else if (movedPiece.getType().equals(PieceType.PAWN) && to.pozY == Chessboard.bottom || to.pozY == Chessboard.top && promotedPiece != null)
        {
            this.promotedTo = promotedPiece;
        }
    }

    public Square getFrom()
    {
        return this.from;
    }

    public Square getTo()
    {
        return this.to;
    }

    public Piece getMovedPiece()
    {
        return this.movedPiece;
    }

    public Piece getTakenPiece()
    {
        return this.takenPiece;
    }

    public boolean wasEnPassant()
    {
        return this.wasEnPassant;
    }

    public boolean wasPawnTwoFieldsMove()
    {
        return this.wasPawnTwoFieldsMove;
    }

    public Moves.castling getCastlingMove()
    {
        return this.castlingMove;
    }

    public Piece getPromotedPiece()
    {
        return this.promotedTo;
    }
}
