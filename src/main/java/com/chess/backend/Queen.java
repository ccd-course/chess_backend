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
 * Class to represent a queen piece
 * Queen can move almost in every way:
 * |_|_|_|X|_|_|_|X|7
    |X|_|_|X|_|_|X|_|6
    |_|X|_|X|_|X|_|_|5
    |_|_|X|X|x|_|_|_|4
    |X|X|X|Q|X|X|X|X|3
    |_|_|X|X|X|_|_|_|2
    |_|X|_|X|_|X|_|_|1
    |X|_|_|X|_|_|X|_|0
    0 1 2 3 4 5 6 7
 */
public class Queen extends Piece
{

    Queen(Chessboard chessboard, Player player)
    {
        super(chessboard, player);//call initializer of super type: Piece
        //this.setImages("Queen-W.png", "Queen-B.png");
        this.symbol = "Q";
    }

    /**
     * Annotation to superclass Piece changing pawns location
     * @return  ArrayList with new possition of piece
     */
    @Override
    public ArrayList allMoves()
    {
        ArrayList list = new ArrayList();

        // ------------- as Rook --------------
        for (int i = this.square.pozY + 1; i <= 7; ++i)
        {//up

            if (this.checkPiece(this.square.pozX, i))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i]))
                    {
                        list.add(chessboard.squares[this.square.pozX][i]);
                    }
                }
                else
                {//or black

                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i]))
                    {
                        list.add(chessboard.squares[this.square.pozX][i]);
                    }
                }

                if (this.otherOwner(this.square.pozX, i))
                {
                    break;
                }
            }
            else //if on this square is piece
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = this.square.pozY - 1; i >= 0; --i)
        {//down

            if (this.checkPiece(this.square.pozX, i))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i]))
                    {
                        list.add(chessboard.squares[this.square.pozX][i]);
                    }
                }
                else
                {//or black

                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][i]))
                    {
                        list.add(chessboard.squares[this.square.pozX][i]);
                    }
                }

                if (this.otherOwner(this.square.pozX, i))
                {
                    break;
                }
            }
            else
            {//if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = this.square.pozX - 1; i >= 0; --i)
        {//left

            if (this.checkPiece(i, this.square.pozY))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY]))
                    {
                        list.add(chessboard.squares[i][this.square.pozY]);
                    }
                }
                else
                {//or black

                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY]))
                    {
                        list.add(chessboard.squares[i][this.square.pozY]);
                    }
                }

                if (this.otherOwner(i, this.square.pozY))
                {
                    break;
                }
            }
            else
            {//if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = this.square.pozX + 1; i <= 7; ++i)
        {//right

            if (this.checkPiece(i, this.square.pozY))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY]))
                    {
                        list.add(chessboard.squares[i][this.square.pozY]);
                    }
                }
                else
                {//or black

                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[i][this.square.pozY]))
                    {
                        list.add(chessboard.squares[i][this.square.pozY]);
                    }
                }

                if (this.otherOwner(i, this.square.pozY))
                {
                    break;
                }
            }
            else
            {//if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        // ------------- as Bishop ------------------
        for (int h = this.square.pozX - 1, i = this.square.pozY + 1; !isout(h, i); --h, ++i)
        {//left-up

            if (this.checkPiece(h, i))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }
                else
                {//or black
                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            {//if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = this.square.pozX - 1, i = this.square.pozY - 1; !isout(h, i); --h, --i)
        {//left-down

            if (this.checkPiece(h, i))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }
                else
                {//or black

                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            {//if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = this.square.pozX + 1, i = this.square.pozY + 1; !isout(h, i); ++h, ++i)
        {//right-up

            if (this.checkPiece(h, i))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }
                else
                {//or black

                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            {//if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = this.square.pozX + 1, i = this.square.pozY - 1; !isout(h, i); ++h, --i)
        {//right-down

            if (this.checkPiece(h, i))
            {//if on this sqhuare isn't piece

                if (this.player.color == Player.colors.white)
                {//white

                    if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }
                else
                {//or black

                    if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[h][i]))
                    {
                        list.add(chessboard.squares[h][i]);
                    }
                }

                if (this.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            { //if on this square is piece

                break;//we've to break becouse we cannot go beside other piece!!
            }
        }
        // ------------------------------------

        return list;
    }
}
