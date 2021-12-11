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

import lombok.Data;

/**
 * Class to represent chessboard.
 *
 * Chessboard is made from squares. It also contains a move history as well as the number of players.
 */
@Data
public class Chessboard {
    public int numberOfPlayers;
    public Square[][] squares;//squares of chessboard
    //    ----------------------------
    //    For En passant:
    //    |-> Pawn whose in last turn moved two square
    public Piece twoSquareMovedPawn = null;
    public Piece twoSquareMovedPawn2 = null;
    private boolean breakCastling = false; //if last move break castling
    private Moves moves_history;
}
