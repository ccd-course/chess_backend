package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.abstractmoves.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.services.ChessboardService;
import com.chess.backend.services.PlayerService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a piece.
 */
public class Piece {
    public Square square;
    public Player player;
    Chessboard chessboard; // <-- this relations isn't in class diagram, but it's necessary :/
    private PieceType type;
    private boolean motioned;
    private final boolean clockwise; // TODO: 4 of the 8 Pawns move in the other direction. Initialize accordingly.


    public Piece(PieceType type, Player player, boolean clockwise) {
        this.type = type;
        this.player = player;
        this.clockwise = clockwise;
    }

    /**
     * Returns all allowed moves of the piece. The moves for each pieceType are composed of several abstract moves.
     *
     * @param game Game context
     * @return A HashSet of all allowed moves of the piece in this individual game context.
     */
    public HashSet<Move> getAllowedFullMoves(Game game) {
        HashSet<Move> allowedMoves = new HashSet<>();

        switch (this.type) {
            case PAWN:
                if (clockwise) {
                    allowedMoves.addAll(MoveOneForward.concretise(game, this.square, false, false, true));
                    if (square.getPosY() == PlayerService.getBaseY(player)) {
                        allowedMoves.addAll(MoveTwoForward.concretise(game, this.square, false, false, true));
                    }
                    allowedMoves.addAll(MovePawnCaptureForward.concretise(game, this.square, true, false, false));
                } else {
                    allowedMoves.addAll(MoveOneBackward.concretise(game, this.square, false, false, true));
                    if (square.getPosY() == PlayerService.getBaseY(player) + 3) {
                        allowedMoves.addAll(MoveTwoBackward.concretise(game, this.square, false, false, true));
                    }
                    allowedMoves.addAll(MovePawnCaptureBackward.concretise(game, this.square, true, false, false));
                }
                break;
            case KING:
                allowedMoves.addAll(MoveOneForward.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveOneBackward.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveOneLeft.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveOneRight.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveOneDiagonal.concretise(game, this.square, true, false, true));
                break;
            case QUEEN:
                allowedMoves.addAll(MoveLeft.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveRight.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveForward.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveBackward.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveDiagonal.concretise(game, this.square, true, false, true));
                break;
            case ROOK:
                allowedMoves.addAll(MoveLeft.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveRight.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveForward.concretise(game, this.square, true, false, true));
                allowedMoves.addAll(MoveBackward.concretise(game, this.square, true, false, true));
                break;
            case BISHOP:
                allowedMoves.addAll(MoveDiagonal.concretise(game, this.square, true, false, true));
                break;
            case KNIGHT:
                allowedMoves.addAll(MoveKnight.concretise(game, this.square, true, true, true));
                break;


        }

        return allowedMoves;
    }

    /**
     * Converts AllowedFullMoves to an array of Squares representing only the destination of the move.
     *
     * @param game Game context
     * @return ArrayList of possible Squares to move to.
     */
    public ArrayList<Square> getAllowedMoves(Game game) {
        Set<Move> allowedFullMoves = getAllowedFullMoves(game);
        ArrayList<Square> allowedMoves = new ArrayList<>();

        for (Move move :
                allowedFullMoves) {
            allowedMoves.add(move.getTo());
        }

        return allowedMoves;
    }

    public boolean getMainDirection() {
        return this.clockwise;
    }

    /**
     * Method to check whether the king is checked
     *
     * @return bool true if king is not save, else returns false
     */
    public boolean isChecked() {
        return !isSafe(this.square);
    }

    /**
     * Method to check is the king is checked or stalemated
     *
     * @return int 0 if nothing, 1 if checkmate, else returns 2
     */
    public int isCheckmatedOrStalemated(Game game) {
        /*
         *returns: 0-nothing, 1-checkmate, 2-stalemate
         */
        if (this.getAllowedMoves(game).size() == 0) {
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (chessboard.getSquares()[i][j].getPiece() != null
                            && chessboard.getSquares()[i][j].getPiece().getPlayer() == this.player
                            && chessboard.getSquares()[i][j].getPiece().getAllowedMoves(game).size() != 0) {
                        return 0;
                    }
                }
            }

            if (this.isChecked()) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 0;
        }
    }

    /**
     * Method to check is the king is checked by an opponent
     *
     * @param s Squere where is a king
     * @return bool true if king is save, else returns false
     */
    private boolean isSafe(Square s) //A bit confusing code.
    {
        // Rook & Queen
        for (int i = s.getPosY() + 1; i <= 7; ++i) //up
        {
            if (this.chessboard.squares[s.getPosX()][i].getPiece() == null || this.chessboard.squares[s.getPosX()][i].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[s.getPosX()][i].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[s.getPosX()][i].getPiece().getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[s.getPosX()][i].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = s.getPosY() - 1; i >= 0; --i) //down
        {
            if (this.chessboard.squares[s.getPosX()][i].getPiece() == null || this.chessboard.squares[s.getPosX()][i].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[s.getPosX()][i].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[s.getPosX()][i].getPiece().getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[s.getPosX()][i].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = s.getPosX() - 1; i >= 0; --i) //left
        {
            if (this.chessboard.squares[i][s.getPosY()].getPiece() == null || this.chessboard.squares[i][s.getPosY()].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[i][s.getPosY()].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[i][s.getPosY()].getPiece().getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[i][s.getPosY()].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = s.getPosX() + 1; i <= 7; ++i) //right
        {
            if (this.chessboard.squares[i][s.getPosY()].getPiece() == null || this.chessboard.squares[i][s.getPosY()].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[i][s.getPosY()].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[i][s.getPosY()].getPiece().getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[i][s.getPosY()].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // Bishop & Queen
        for (int h = s.getPosX() - 1, i = s.getPosY() + 1; !isout(h, i); --h, ++i) //left-up
        {
            if (this.chessboard.squares[h][i].getPiece() == null || this.chessboard.squares[h][i].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[h][i].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for (int h = s.getPosX() - 1, i = s.getPosY() - 1; !isout(h, i); --h, --i) //left-down
        {
            if (this.chessboard.squares[h][i].getPiece() == null || this.chessboard.squares[h][i].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[h][i].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for (int h = s.getPosX() + 1, i = s.getPosY() + 1; !isout(h, i); ++h, ++i) //right-up
        {
            if (this.chessboard.squares[h][i].getPiece() == null || this.chessboard.squares[h][i].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[h][i].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for (int h = s.getPosX() + 1, i = s.getPosY() - 1; !isout(h, i); ++h, --i) //right-down
        {
            if (this.chessboard.squares[h][i].getPiece() == null || this.chessboard.squares[h][i].getPiece() == this) //if on this sqhuare isn't piece
            {
                continue;
            } else if (this.chessboard.squares[h][i].getPiece().player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].getPiece().getType().equals(PieceType.QUEEN)) {
                    return false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // Knight
        int newX, newY;

        //1
        newX = s.getPosX() - 2;
        newY = s.getPosY() + 1;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        //2
        newX = s.getPosX() - 1;
        newY = s.getPosY() + 2;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        //3
        newX = s.getPosX() + 1;
        newY = s.getPosY() + 2;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        //4
        newX = s.getPosX() + 2;
        newY = s.getPosY() + 1;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        //5
        newX = s.getPosX() + 2;
        newY = s.getPosY() - 1;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        //6
        newX = s.getPosX() + 1;
        newY = s.getPosY() - 2;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        //7
        newX = s.getPosX() - 1;
        newY = s.getPosY() - 2;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        //8
        newX = s.getPosX() - 2;
        newY = s.getPosY() - 1;

        if (!isout(newX, newY)) {
            if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
            {
            } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.KNIGHT)) {
                return false;
            }
        }

        // King
        Piece otherKing;

        // TODO: Extend to work with more than 2 Players. Players should not be null here.
        if (this == ChessboardService.searchSquaresByPiece(chessboard.squares, PieceType.KING, Color.WHITE, null).get(0).getPiece()) {
            otherKing = ChessboardService.searchSquaresByPiece(chessboard.squares, PieceType.KING, Color.BLACK, null).get(0).getPiece();
        } else {
            otherKing = ChessboardService.searchSquaresByPiece(chessboard.squares, PieceType.KING, Color.WHITE, null).get(0).getPiece();
        }

        if (s.getPosX() <= otherKing.square.getPosX() + 1
                && s.getPosY() >= otherKing.square.getPosX() - 1
                && s.getPosY() <= otherKing.square.getPosY() + 1
                && s.getPosY() >= otherKing.square.getPosY() - 1) {
            return false;
        }

        // Pawn
        if (this.player.goDown) //check if player "go" down or up
        {//System.out.println("go down");
            newX = s.getPosX() - 1;
            newY = s.getPosY() + 1;
            if (!isout(newX, newY)) {
                if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
                {
                } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
                {
                } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.PAWN)) {
                    return false;
                }
            }
            newX = s.getPosX() + 1;
            if (!isout(newX, newY)) {
                if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
                {
                } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
                {
                } else return !this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.PAWN);
            }
        } else {//System.out.println("go up");
            newX = s.getPosX() - 1;
            newY = s.getPosY() - 1;
            if (!isout(newX, newY)) {
                if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
                {
                } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
                {
                } else if (this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.PAWN)) {
                    return false;
                }
            }
            newX = s.getPosX() + 1;
            if (!isout(newX, newY)) {
                if (this.chessboard.squares[newX][newY].getPiece() == null) //if on this sqhuare isn't piece
                {
                } else if (this.chessboard.squares[newX][newY].getPiece().player == this.player) //if is our piece
                {
                } else return !this.chessboard.squares[newX][newY].getPiece().getType().equals(PieceType.PAWN);
            }
        }

        return true;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public Color getColor() {
        return this.player.getColor();
    }

    /**
     * Method is useful for out of bounds protection
     *
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if parameters are out of bounds (array)
     */
    protected boolean isout(int x, int y) {
        return x < 0 || x > 7 || y < 0 || y > 7;
    }

    /**
     * @param x y position on chessboard
     * @param y y position on chessboard
     * @return true if can move, false otherwise
     */
    protected boolean checkPiece(int x, int y) {
        if (chessboard.squares[x][y].getPiece() != null
                && chessboard.squares[x][y].getPiece().getType().equals(PieceType.KING)) {
            return false;
        }
        Piece piece = chessboard.squares[x][y].getPiece();
        //or piece is another player
        return piece == null || //if this sqhuare is empty
                piece.player != this.player;
    }

    /**
     * Method check if piece has other owner than calling piece
     *
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if owner(player) is different
     */
    protected boolean otherOwner(int x, int y) {
        Square sq = chessboard.squares[x][y];
        if (sq.getPiece() == null) {
            return false;
        }
        return this.player != sq.getPiece().player;
    }

    public String getSymbol() {
        return this.getType().symbol;
    }

    public boolean isMotioned() {
        return motioned;
    }

    public void setMotioned(boolean motioned) {
        this.motioned = motioned;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", chessboard=" + chessboard +
                ", player=" + player +
                ", motioned=" + motioned +
                ", clockwise=" + clockwise +
                '}';
    }
}
