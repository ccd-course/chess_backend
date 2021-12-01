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

import javax.swing.*;
import java.io.BufferedReader;
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
    public Chessboard chessboard;
    private Player activePlayer;
    public GameClock gameClock;
    public Moves moves;

    public Game()
    {
        this.moves = new Moves(this);
        settings = new Settings();
        chessboard = new Chessboard(this.settings, this.moves);
        //this.chessboard.
        // gameClock = new GameClock(this); // TODO: Implement from old jchess

    }

    /** Method to Start new game
     *
     */
    public void newGame()
    {
        chessboard.setPieces("", settings.playerWhite, settings.playerBlack);
        activePlayer = settings.playerWhite;
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
        System.out.println("next move, active player: " + activePlayer.name + ", color: " + activePlayer.color.name());
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
            if (chessboard.squares[beginX][beginY].piece.getAllowedMoves(this).contains(chessboard.squares[endX][endY])) //move
            {
                chessboard.move(chessboard.squares[beginX][beginY], chessboard.squares[endX][endY]);
            }
            else
            {
                System.out.println("Bad move");
                return false;
            }
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