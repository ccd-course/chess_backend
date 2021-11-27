/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package com.chess.backend;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
Class to represent a piece (any kind) - this class should be extended to represent pawn, bishop etc.
 */
public abstract class Piece
{

    Chessboard chessboard; // <-- this relations isn't in class diagram, but it's necessary :/
    public Square square;
    public Player player;
    String name;
    protected String symbol;

    Piece(Chessboard chessboard, Player player)
    {
        this.chessboard = chessboard;
        this.player = player;
        this.name = this.getClass().getSimpleName();

    }

    abstract public ArrayList allMoves();

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
                && chessboard.squares[x][y].piece.name.equals("King"))
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
        return this.symbol;
    }
}
