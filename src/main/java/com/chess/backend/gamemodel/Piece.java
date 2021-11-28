package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.abstractmoves.MoveDiagonal;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;

import java.util.ArrayList;

public class Piece {
    private PieceType type;
    private Color color;
    Chessboard chessboard; // <-- this relations isn't in class diagram, but it's necessary :/
    public Square square;
    public Player player;
    private boolean motioned;
    private boolean clockwise; // TODO: 4 of the 8 Pawns move in the other direction. Initialize accordingly.

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public ArrayList<Move> getAllowedFullMoves(Game game){
        ArrayList<Move> allowedMoves = new ArrayList<>();

        switch (this.type){
            case PAWN:
                allowedMoves.addAll(MoveDiagonal.concretise(game, false, false));
        }

        return allowedMoves;
    }

    /** The old jchess components expect an ArrayList of Squares instead of Moves. Therefore we need to convert our
     * AllowedFullMoves.
     *  @return ArrayList of possible Squares to move to.
     */
    public ArrayList<Square> getAllowedMoves(Game game){
        ArrayList<Move> allowedFullMoves = getAllowedFullMoves(game);
        ArrayList<Square> allowedMoves = new ArrayList<>();

        for (Move move:
             allowedFullMoves) {
            allowedMoves.add(move.getTo());
        }

        return allowedMoves;
    }

    public boolean getMainDirection(){
        return this.clockwise;
    }

    /** Method to check is the king is checked
     *  @return bool true if king is not save, else returns false
     */
    public boolean isChecked()
    {
        return !isSafe(this.square);
    }

    /** Method to check is the king is checked or stalemated
     *  @return int 0 if nothing, 1 if checkmate, else returns 2
     */
    public int isCheckmatedOrStalemated(Game game)
    {
        /*
         *returns: 0-nothing, 1-checkmate, 2-stalemate
         */
        if (this.getAllowedMoves(game).size() == 0)
        {
            for (int i = 0; i < 8; ++i)
            {
                for (int j = 0; j < 8; ++j)
                {
                    if (chessboard.squares[i][j].piece != null
                            && chessboard.squares[i][j].piece.player == this.player
                            && chessboard.squares[i][j].piece.getAllowedMoves(game).size() != 0)
                    {
                        return 0;
                    }
                }
            }

            if (this.isChecked())
            {
                return 1;
            }
            else
            {
                return 2;
            }
        }
        else
        {
            return 0;
        }
    }

    /** Method to check is the king is checked by an opponent
     * @param s Squere where is a king
     * @return bool true if king is save, else returns false
     */
    private boolean isSafe(Square s) //A bit confusing code.
    {
        // Rook & Queen
        for (int i = s.pozY + 1; i <= 7; ++i) //up
        {
            if (this.chessboard.squares[s.pozX][i].piece == null || this.chessboard.squares[s.pozX][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[s.pozX][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[s.pozX][i].piece.getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[s.pozX][i].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.pozY - 1; i >= 0; --i) //down
        {
            if (this.chessboard.squares[s.pozX][i].piece == null || this.chessboard.squares[s.pozX][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[s.pozX][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[s.pozX][i].piece.getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[s.pozX][i].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.pozX - 1; i >= 0; --i) //left
        {
            if (this.chessboard.squares[i][s.pozY].piece == null || this.chessboard.squares[i][s.pozY].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[i][s.pozY].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[i][s.pozY].piece.getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[i][s.pozY].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.pozX + 1; i <= 7; ++i) //right
        {
            if (this.chessboard.squares[i][s.pozY].piece == null || this.chessboard.squares[i][s.pozY].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[i][s.pozY].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[i][s.pozY].piece.getType().equals(PieceType.ROOK)
                        || this.chessboard.squares[i][s.pozY].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        // Bishop & Queen
        for (int h = s.pozX - 1, i = s.pozY + 1; !isout(h, i); --h, ++i) //left-up
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.pozX - 1, i = s.pozY - 1; !isout(h, i); --h, --i) //left-down
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.pozX + 1, i = s.pozY + 1; !isout(h, i); ++h, ++i) //right-up
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.pozX + 1, i = s.pozY - 1; !isout(h, i); ++h, --i) //right-down
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.getType().equals(PieceType.BISHOP)
                        || this.chessboard.squares[h][i].piece.getType().equals(PieceType.QUEEN))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        // Knight
        int newX, newY;

        //1
        newX = s.pozX - 2;
        newY = s.pozY + 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        //2
        newX = s.pozX - 1;
        newY = s.pozY + 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        //3
        newX = s.pozX + 1;
        newY = s.pozY + 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        //4
        newX = s.pozX + 2;
        newY = s.pozY + 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        //5
        newX = s.pozX + 2;
        newY = s.pozY - 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        //6
        newX = s.pozX + 1;
        newY = s.pozY - 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        //7
        newX = s.pozX - 1;
        newY = s.pozY - 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        //8
        newX = s.pozX - 2;
        newY = s.pozY - 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.KNIGHT))
            {
                return false;
            }
        }

        // King
        Piece otherKing;
        if (this == chessboard.kingWhite)
        {
            otherKing = chessboard.kingBlack;
        }
        else
        {
            otherKing = chessboard.kingWhite;
        }

        if (s.pozX <= otherKing.square.pozX + 1
                && s.pozX >= otherKing.square.pozX - 1
                && s.pozY <= otherKing.square.pozY + 1
                && s.pozY >= otherKing.square.pozY - 1)
        {
            return false;
        }

        // Pawn
        if (this.player.goDown) //check if player "go" down or up
        {//System.out.println("go down");
            newX = s.pozX - 1;
            newY = s.pozY + 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.PAWN))
                {
                    return false;
                }
            }
            newX = s.pozX + 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.PAWN))
                {
                    return false;
                }
            }
        }
        else
        {//System.out.println("go up");
            newX = s.pozX - 1;
            newY = s.pozY - 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.PAWN))
                {
                    return false;
                }
            }
            newX = s.pozX + 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.getType().equals(PieceType.PAWN))
                {
                    return false;
                }
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
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /** Method is useful for out of bounds protection
     * @param x  x position on chessboard
     * @param y y position on chessboard
     * @return true if parameters are out of bounds (array)
     * */
    protected boolean isout(int x, int y)
    {
        if (x < 0 || x > 7 || y < 0 || y > 7)
        {
            return true;
        }
        return false;
    }

    /**
     * @param x y position on chessboard
     * @param y  y position on chessboard
     * @return true if can move, false otherwise
     * */
    protected boolean checkPiece(int x, int y)
    {
        if (chessboard.squares[x][y].piece != null
                && chessboard.squares[x][y].piece.getType().equals(PieceType.KING))
        {
            return false;
        }
        Piece piece = chessboard.squares[x][y].piece;
        if (piece == null || //if this sqhuare is empty
                piece.player != this.player) //or piece is another player
        {
            return true;
        }
        return false;
    }

    /** Method check if piece has other owner than calling piece
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if owner(player) is different
     * */
    protected boolean otherOwner(int x, int y)
    {
        Square sq = chessboard.squares[x][y];
        if (sq.piece == null)
        {
            return false;
        }
        if (this.player != sq.piece.player)
        {
            return true;
        }
        return false;
    }

    public String getSymbol()
    {
        return this.getType().symbol;
    }

    public boolean isMotioned() {
        return motioned;
    }

    public void setMotioned(boolean motioned) {
        this.motioned = motioned;
    }
}
