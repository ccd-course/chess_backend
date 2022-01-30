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

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class representing the players moves history.
 * <p>
 * It also checks that the moves taken by the player are correct.
 * All moves which were made by the current player saved as a list of strings.
 */
@Data
public class Moves {

    private Stack<Move> moveBackStack = new Stack<Move>();
    private Stack<Move> moveForwardStack = new Stack<Move>();
    private final ArrayList<String> move = new ArrayList<String>();
    private final int columnsNum = 3;
    private int rowsNum = 0;
    private boolean enterBlack = false;
    private final ChessGame game;

    Moves(ChessGame game) {
        this.game = game;
    }

}
