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

import com.chess.backend.gamemodel.constants.Color;
import lombok.Data;

import java.io.Serializable;


/**
 * Class representing the player in the game
 */
@Data
public class Player implements Serializable
{

    String name;
    Color color;
    int id;
    boolean goDown;

    public Player()
    {
    }

    public Player(String name, int id)
    {
        this.name = name;
        this.id  = id;
    }
    public Player(String name, Color color)
    {
        this.name = name;
//        this.color = color;
//        this.goDown = false;
    }

    public Player(String name, Color color, Integer id)
    {
        this.name = name;
        this.color = color;
        this.id = id;
//        this.goDown = false;
    }
    /** Method setting the players name
     *  @param name name of player
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /** Method getting the players name
     *  @return name of player
     */
    String getName()
    {
        return this.name;
    }

//    public Color getColor() {
//        return color;
//    }

//    public void setColor(Color color) {
//        this.color = color;
//    }

//    public boolean isGoDown() {
//        return goDown;
//    }
//
//    public void setGoDown(boolean goDown) {
//        this.goDown = goDown;
//    }
}
