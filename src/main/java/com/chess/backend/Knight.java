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
import java.util.ArrayList;

/**
 * Class to represent a chess pawn knight
 */
public class Knight extends Piece
{
    Knight(Chessboard chessboard, Player player)
    {
        super(chessboard, player);//call initializer of super type: Piece
        //this.setImages("Knight-W.png", "Knight-B.png");
        this.symbol = "N";
    }

    /**
     *  Annotation to superclass Piece changing pawns location
     * @return  ArrayList with new possition of pawn
     */
    @Override
    public ArrayList allMoves()
    {
        ArrayList list = new ArrayList();

        // knight all moves
        //  _______________ Y:
        // |_|_|_|_|_|_|_|_|7
        // |_|_|_|_|_|_|_|_|6
        // |_|_|2|_|3|_|_|_|5
        // |_|1|_|_|_|4|_|_|4
        // |_|_|_|K|_|_|_|_|3
        // |_|8|_|_|_|5|_|_|2
        // |_|_|7|_|6|_|_|_|1
        // |_|_|_|_|_|_|_|_|0
        //X:0 1 2 3 4 5 6 7
        //

        int newX, newY;

        //1
        newX = this.square.pozX - 2;
        newY = this.square.pozY + 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //2
        newX = this.square.pozX - 1;
        newY = this.square.pozY + 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //3
        newX = this.square.pozX + 1;
        newY = this.square.pozY + 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //4
        newX = this.square.pozX + 2;
        newY = this.square.pozY + 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //5
        newX = this.square.pozX + 2;
        newY = this.square.pozY - 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //6
        newX = this.square.pozX + 1;
        newY = this.square.pozY - 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //7
        newX = this.square.pozX - 1;
        newY = this.square.pozY - 2;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //8
        newX = this.square.pozX - 2;
        newY = this.square.pozY - 1;

        if (!isout(newX, newY) && checkPiece(newX, newY))
        {
            if (this.player.color == Player.colors.white) //white
            {
                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        return list;
    }
}
