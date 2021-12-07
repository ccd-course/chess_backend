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
package com.chess.backend.gamemodel;

/**
 * Class to represent a chessboard square
 */
public class Square
{

    int pozX; // 0-7, becouse 8 squares for row/column
    int pozY; // 0-7, becouse 8 squares for row/column
    public Piece piece = null;//object Piece on square (and extending Piecie)

    public Square(int pozX, int pozY, Piece piece)
    {
        this.pozX = pozX;
        this.pozY = pozY;
        this.piece = piece;
    }/*--endOf-Square--*/


    Square(Square square)
    {
        this.pozX = square.pozX;
        this.pozY = square.pozY;
        this.piece = square.piece;
    }

    public Square clone(Square square)
    {
        return new Square(square);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
        this.piece.square = this;
    }

    public void removePiece(){
        this.piece = null;
    }

    public int getPozX() {
        return pozX;
    }

    public int getPozY() {
        return pozY;
    }
}
