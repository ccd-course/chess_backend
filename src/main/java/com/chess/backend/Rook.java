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
 * Class to represent a chess pawn rook
 * Rook can move:
 *       |_|_|_|X|_|_|_|_|7
|_|_|_|X|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|X|X|X|B|X|X|X|X|3
|_|_|_|X|_|_|_|_|2
|_|_|_|X|_|_|_|_|1
|_|_|_|X|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 */
public class Rook extends Piece
{

    boolean wasMotion = false;
    protected static final Image imageWhite = GUI.loadImage("Rook-W.png");
    protected static final Image imageBlack = GUI.loadImage("Rook-B.png");
    public static short value = 5;

    Rook(Chessboard chessboard, Player player)
    {
        super(chessboard, player);//call initializer of super type: Piece
        //this.setImages("Rook-W.png", "Rook-B.png");
        this.symbol = "R";
        this.setImage();
    }

    @Override
    void setImage()
    {
        if (this.player.color == this.player.color.black)
        {
            image = imageBlack;
        }
        else
        {
            image = imageWhite;
        }
        orgImage = image;
    }

    /**
     *  Annotation to superclass Piece changing pawns location
     * @return  ArrayList with new possition of piece
     */
    @Override
    public ArrayList allMoves()
    {
        ArrayList list = new ArrayList();

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
            else
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
            {
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
            {
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
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        return list;
    }
}
