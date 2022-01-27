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

import com.chess.backend.domain.models.IBoard;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import lombok.Data;

import java.util.ArrayList;

/**
 * Class to represent chessboard.
 *
 * Chessboard is made from squares. It also contains a move history as well as the number of players.
 */
@Data
@IgnoreExtraProperties
public class Chessboard implements IBoard, Cloneable {
    private int numberOfPlayers;
    private ArrayList<ArrayList<Square>>  squares;//squares of chessboard
    private boolean breakCastling = false; //if last move break castling
    private Moves moves_history;

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}