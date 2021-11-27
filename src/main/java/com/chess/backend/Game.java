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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
public class Game
{

    public Settings settings;
    public boolean blockedChessboard;
    public Chessboard chessboard;
    private Player activePlayer;
    public GameClock gameClock;
    public Moves moves;

    Game()
    {
        this.moves = new Moves(this);
        settings = new Settings();
        chessboard = new Chessboard(this.settings, this.moves);
        //this.chessboard.
        gameClock = new GameClock(this);

        this.blockedChessboard = false;
    }


    /** Method checking in with of line there is an error
     *  @param  br BufferedReader class object to operate on
     *  @param  srcStr String class object with text which variable you want to get in file
     *  @return String with searched variable in file (whole line)
     *  @throws ReadGameError class object when something goes wrong when reading file
     */
    static public String getLineWithVar(BufferedReader br, String srcStr) throws ReadGameError
    {
        String str = new String();
        while (true)
        {
            try
            {
                str = br.readLine();
            }
            catch (java.io.IOException exc)
            {
                System.out.println("Something wrong reading file: " + exc);
            }
            if (str == null)
            {
                throw new ReadGameError();
            }
            if (str.startsWith(srcStr))
            {
                return str;
            }
        }
    }

    /** Method to get value from loaded txt line
     *  @param line Line which is readed
     *  @return result String with loaded value
     *  @throws ReadGameError object class when something goes wrong
     */
    static public String getValue(String line) throws ReadGameError
    {
        //System.out.println("getValue called with: "+line);
        int from = line.indexOf("\"");
        int to = line.lastIndexOf("\"");
        int size = line.length() - 1;
        String result = new String();
        if (to < from || from > size || to > size || to < 0 || from < 0)
        {
            throw new ReadGameError();
        }
        try
        {
            result = line.substring(from + 1, to);
        }
        catch (StringIndexOutOfBoundsException exc)
        {
            System.out.println("error getting value: " + exc);
            return "none";
        }
        return result;
    }

    /** Method to Start new game
     *
     */
    public void newGame()
    {
        chessboard.setPieces("", settings.playerWhite, settings.playerBlack);

        //System.out.println("new game, game type: "+settings.gameType.name());

        activePlayer = settings.playerWhite;
        if (activePlayer.playerType != Player.playerTypes.localUser)
        {
            this.blockedChessboard = true;
        }
    }

    /** Method to end game
     *  @param message what to show player(s) at end of the game (for example "draw", "black wins" etc.)
     */
    public void endGame(String message)
    {
        this.blockedChessboard = true;
        System.out.println(message);
        JOptionPane.showMessageDialog(null, message);
    }

    /** Method to swich active players after move
     */
    public void switchActive()
    {
        if (activePlayer == settings.playerWhite)
        {
            activePlayer = settings.playerBlack;
        }
        else
        {
            activePlayer = settings.playerWhite;
        }

        this.gameClock.switch_clocks();
    }

    /** Method of getting accualy active player
     *  @return  player The player which have a move
     */
    public Player getActivePlayer()
    {
        return this.activePlayer;
    }

    /** Method to go to next move (checks if game is local/network etc.)
     */
    public void nextMove()
    {
        switchActive();

        System.out.println("next move, active player: " + activePlayer.name + ", color: " + activePlayer.color.name() + ", type: " + activePlayer.playerType.name());
        if (activePlayer.playerType == Player.playerTypes.localUser)
        {
            this.blockedChessboard = false;
        }
        else if (activePlayer.playerType == Player.playerTypes.networkUser)
        {
            this.blockedChessboard = true;
        }
        else if (activePlayer.playerType == Player.playerTypes.computer)
        {
        }
    }

    /** Method to simulate Move to check if it's correct etc. (usable for network game).
     * @param beginX from which X (on chessboard) move starts
     * @param beginY from which Y (on chessboard) move starts
     * @param endX   to   which X (on chessboard) move go
     * @param endY   to   which Y (on chessboard) move go
     * */
    public boolean simulateMove(int beginX, int beginY, int endX, int endY)
    {
        try 
        {
            chessboard.select(chessboard.squares[beginX][beginY]);
            if (chessboard.activeSquare.piece.allMoves().indexOf(chessboard.squares[endX][endY]) != -1) //move
            {
                chessboard.move(chessboard.squares[beginX][beginY], chessboard.squares[endX][endY]);
            }
            else
            {
                System.out.println("Bad move");
                return false;
            }
            chessboard.unselect();
            nextMove();

            return true;
            
        } 
        catch(StringIndexOutOfBoundsException exc) 
        {
            return false;
        }    
        catch(ArrayIndexOutOfBoundsException exc) 
        {
            return false;
        }
        catch(NullPointerException exc)
        {
            return false;
        }
        finally
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, "ERROR");
        }
    }
}

class ReadGameError extends Exception
{
}