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

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/** Class to representing the full game time
 */
public class GameClock implements Runnable
{

    public Clock clock1;
    public Clock clock2;
    private Clock runningClock;
    private Settings settings;
    private Thread thread;
    private Game game;
    private Graphics g;
    private String white_clock, black_clock;
    private BufferedImage background;
    private Graphics bufferedGraphics;

    GameClock(Game game)
    {
        super();
        this.clock1 = new Clock();//white player clock
        this.clock2 = new Clock();//black player clock
        this.runningClock = this.clock1;//running/active clock
        this.game = game;
        this.settings = game.settings;
        this.background = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

        int time = this.settings.getTimeForGame();

        this.setTimes(time, time);
        this.setPlayers(this.settings.playerBlack, this.settings.playerWhite);

        this.thread = new Thread(this);
        if (this.settings.timeLimitSet)
        {
            thread.start();
        }
    }

    /** Method to init game clock
     */
    public void start()
    {
        this.thread.start();
    }

    /** Method to stop game clock
     */
    public void stop()
    {
        this.runningClock = null;

        try
        {//block this thread
            this.thread.wait();
        }
        catch (InterruptedException exc)
        {
            System.out.println("Error blocking thread: " + exc);
        }
        catch (IllegalMonitorStateException exc1)
        {
            System.out.println("Error blocking thread: " + exc1);
        }
    }


    /** Method of swiching the players clocks
     */
    public void switch_clocks()
    {
        /*in documentation this method is called 'switch', but it's restricted name
        to switch block (in pascal called "case") - this've to be repaired in documentation by Wąsu:P*/
        if (this.runningClock == this.clock1)
        {
            this.runningClock = this.clock2;
        }
        else
        {
            this.runningClock = this.clock1;
        }
    }

    /** Method with is setting the players clocks time
     * @param t1 Capt the player time
     * @param t2 Capt the player time
     */
    public void setTimes(int t1, int t2)
    {
        /*rather in chess game players got the same time 4 game, so why in documentation
         * this method've 2 parameters ? */
        this.clock1.init(t1);
        this.clock2.init(t2);
    }

    /** Method with is setting the players clocks
     * @param p1 Capt player information
     * @param p2 Capt player information
     */
    private void setPlayers(Player p1, Player p2)
    {
        /*in documentation it's called 'setPlayer' but when we've 'setTimes' better to use
         * one convention of naming methods - this've to be repaired in documentation by Wąsu:P
        dojdziemy do tego:D:D:D*/
        if (p1.color == p1.color.white)
        {
            this.clock1.setPlayer(p1);
            this.clock2.setPlayer(p2);
        }
        else
        {
            this.clock1.setPlayer(p2);
            this.clock2.setPlayer(p1);
        }
    }

    /** Method with is running the time on clock
     */
    public void run()
    {
        while (true)
        {
            if (this.runningClock != null)
            {
                if (this.runningClock.decrement())
                {
                    try
                    {
                        thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        System.out.println("Some error in gameClock thread: " + e);
                    }
                    //if(this.game.blockedChessboard)
                    //  this.game.blockedChessboard = false;
                }
                if (this.runningClock != null && this.runningClock.get_left_time() == 0)
                {
                    this.timeOver();
                }
            }
        }
    }

    /** Method of checking is the time of the game is not over
     */
    private void timeOver()
    {
        String color = new String();
        if (this.clock1.get_left_time() == 0)
        {//Check which player win
            color = this.clock2.getPlayer().color.toString();
        }
        else if (this.clock2.get_left_time() == 0)
        {
            color = this.clock1.getPlayer().color.toString();
        }
        else
        {//if called in wrong moment
            System.out.println("Time over called when player got time 2 play");
        }
        this.game.endGame("Time is over! " + color + " player win the game.");
        this.stop();

        //JOptionPane.showMessageDialog(this, "koniec czasu");
    }
}
