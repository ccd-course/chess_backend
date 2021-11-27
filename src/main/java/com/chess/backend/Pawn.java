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
 * Class to represent a pawn piece
 * Pawn can move only forward and can beat only across
 * In first move pawn can move 2 squares
 * pawn can be upgrade to rook, knight, bishop, Queen if it's in the
 * squares nearest the side where opponent is located
 * First move of pawn:
 *       |_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 * Move of a pawn:
 *       |_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 * Beats with can take pawn:
 *       |_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|X|_|X|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 */
public class Pawn extends Piece
{
    Pawn(Chessboard chessboard, Player player)
    {
        super(chessboard, player);
        //this.setImages("Pawn-W.png", "Pawn-B.png");
        this.symbol = "";
    }

    /**
     * Annotation to superclass Piece changing pawns location
     * @return  ArrayList with new possition of piece
     */
    @Override
    public ArrayList allMoves()
    {
        //System.out.println(this.player.goDown);//4test
        ArrayList list = new ArrayList();
        Square sq;
        Square sq1;
        int first = this.square.pozY - 1;//number where to move
        int second = this.square.pozY - 2;//number where to move (only in first move)
        if (this.player.goDown)
        {//check if player "go" down or up
            first = this.square.pozY + 1;//if yes, change value
            second = this.square.pozY + 2;//if yes, change value
        }
        if (this.isout(first, first))
        {//out of bounds protection
            return list;//return empty list
        }
        sq = chessboard.squares[this.square.pozX][first];
        if (sq.piece == null)
        {//if next is free
            //list.add(sq);//add
            if (this.player.color == Player.colors.white)
            {//white

                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][first]))
                {
                    list.add(chessboard.squares[this.square.pozX][first]);
                }
            }
            else
            {//or black

                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][first]))
                {
                    list.add(chessboard.squares[this.square.pozX][first]);
                }
            }

            if ((player.goDown && this.square.pozY == 1) || (!player.goDown && this.square.pozY == 6))
            {
                sq1 = chessboard.squares[this.square.pozX][second];
                if (sq1.piece == null)
                {
                    //list.add(sq1);//only in first move
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][second]))
                        {
                            list.add(chessboard.squares[this.square.pozX][second]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][second]))
                        {
                            list.add(chessboard.squares[this.square.pozX][second]);
                        }
                    }
                }
            }
        }
        if (!this.isout(this.square.pozX - 1, this.square.pozY)) //out of bounds protection
        {
            //capture
            sq = chessboard.squares[this.square.pozX - 1][first];
            if (sq.piece != null)
            {//check if can hit left
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[this.square.pozX - 1][this.square.pozY];
            if (sq.piece != null
                    && this.chessboard.twoSquareMovedPawn != null
                    && sq == this.chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                }
            }
        }
        if (!this.isout(this.square.pozX + 1, this.square.pozY))
        {//out of bounds protection

            //capture
            sq = chessboard.squares[this.square.pozX + 1][first];
            if (sq.piece != null)
            {//check if can hit right
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    { //white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[this.square.pozX + 1][this.square.pozY];
            if (sq.piece != null
                    && this.chessboard.twoSquareMovedPawn != null
                    && sq == this.chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                }
            }
        }

        return list;
    }
}
