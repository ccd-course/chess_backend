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
 * Class to represent seperate wall-clock for one player.
 * Full ChessClock is represented by GameClock object (two clock - one for each player)
 */
public class Clock {

    private int time_left;
    private Player player;

    Clock() {
        this.init(time_left);
    }

    Clock(int time) {
        this.init(time);
    }

    /**
     * Method to init clock with given value
     *
     * @param time tell method with how much time init clock
     */
    public void init(int time) {
        this.time_left = time;
    }

    /**
     * Method to decrement value of left time
     *
     * @return bool true if time_left > 0, else returns false
     */
    public boolean decrement() {
        if (this.time_left > 0) {
            this.time_left = this.time_left - 1;
            return true;
        }
        return false;
    }

    public void pause() {
    }

    /**
     * Method to get left time in seconds
     *
     * @return Player int integer of seconds
     */
    public int get_left_time() {
        return this.time_left;
    }

    /**
     * Method to get player (owner of this clock)
     *
     * @return Reference to player class object
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Method to get player (owner of this clock)
     *
     * @param player player to set as owner of clock
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Method to prepare time in nice looking String
     *
     * @return String of actual left game time with ':' digits in mm:ss format
     */
    public String prepareString() {
        String strMin = "";
        int time_min = this.get_left_time() / 60;
        int time_sec = this.get_left_time() % 60;
        if (time_min < 10) {//prepare MINUTES
            strMin = "0" + time_min;
        } else {
            strMin = String.valueOf(time_min);
        }
        String result = strMin + ":";
        if (time_sec < 10) {//prepare SECONDS
            result = result + "0" + time_sec;
        } else {
            result = result + time_sec;
        }

        return result;
    }
}
