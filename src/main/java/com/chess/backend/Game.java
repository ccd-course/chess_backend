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
public class Game extends JPanel implements MouseListener, ComponentListener
{

    public Settings settings;
    public boolean blockedChessboard;
    public Chessboard chessboard;
    private Player activePlayer;
    public GameClock gameClock;
    public Client client;
    public Moves moves;
    public Chat chat;

    Game()
    {
        this.setLayout(null);
        this.moves = new Moves(this);
        settings = new Settings();
        chessboard = new Chessboard(this.settings, this.moves);
        chessboard.setVisible(true);
        chessboard.setSize(Chessboard.img_height, Chessboard.img_widht);
        chessboard.addMouseListener(this);
        chessboard.setLocation(new Point(0, 0));
        this.add(chessboard);
        //this.chessboard.
        gameClock = new GameClock(this);
        gameClock.setSize(new Dimension(200, 100));
        gameClock.setLocation(new Point(500, 0));
        this.add(gameClock);

        JScrollPane movesHistory = this.moves.getScrollPane();
        movesHistory.setSize(new Dimension(180, 350));
        movesHistory.setLocation(new Point(500, 121));
        this.add(movesHistory);

        this.chat = new Chat();
        this.chat.setSize(new Dimension(380, 100));
        this.chat.setLocation(new Point(0, 500));
        this.chat.setMinimumSize(new Dimension(400, 100));

        this.blockedChessboard = false;
        this.setLayout(null);
        this.addComponentListener(this);
        this.setDoubleBuffered(true);
    }

    /** Method to save actual state of game
     * @param path address of place where game will be saved
     */
    public void saveGame(File path)
    {
        File file = path;
        FileWriter fileW = null;
        try
        {
            fileW = new FileWriter(file);
        }
        catch (java.io.IOException exc)
        {
            System.err.println("error creating fileWriter: " + exc);
            JOptionPane.showMessageDialog(this, Settings.lang("error_writing_to_file")+": " + exc);
            return;
        }
        Calendar cal = Calendar.getInstance();
        String str = new String("");
        String info = new String("[Event \"Game\"]\n[Date \"" + cal.get(cal.YEAR) + "." + (cal.get(cal.MONTH) + 1) + "." + cal.get(cal.DAY_OF_MONTH) + "\"]\n"
                + "[White \"" + this.settings.playerWhite.name + "\"]\n[Black \"" + this.settings.playerBlack.name + "\"]\n\n");
        str += info;
        str += this.moves.getMovesInString();
        try
        {
            fileW.write(str);
            fileW.flush();
            fileW.close();
        }
        catch (java.io.IOException exc)
        {
            System.out.println("error writing to file: " + exc);
            JOptionPane.showMessageDialog(this, Settings.lang("error_writing_to_file")+": " + exc);
            return;
        }
        JOptionPane.showMessageDialog(this, Settings.lang("game_saved_properly"));
    }

    /** Loading game method(loading game state from the earlier saved file)
     *  @param file File where is saved game
     */

    /*@Override
    public void setSize(int width, int height) {
    Dimension min = this.getMinimumSize();
    if(min.getHeight() < height && min.getWidth() < width) {
    super.setSize(width, height);
    } else if(min.getHeight() < height) {
    super.setSize(width, (int)min.getHeight());
    } else if(min.getWidth() < width) {
    super.setSize((int)min.getWidth(), height);
    } else {
    super.setSize(width, height);
    }
    }*/
    static public void loadGame(File file)
    {
        FileReader fileR = null;
        try
        {
            fileR = new FileReader(file);
        }
        catch (java.io.IOException exc)
        {
            System.out.println("Something wrong reading file: " + exc);
            return;
        }
        BufferedReader br = new BufferedReader(fileR);
        String tempStr = new String();
        String blackName, whiteName;
        try
        {
            tempStr = getLineWithVar(br, new String("[White"));
            whiteName = getValue(tempStr);
            tempStr = getLineWithVar(br, new String("[Black"));
            blackName = getValue(tempStr);
            tempStr = getLineWithVar(br, new String("1."));
        }
        catch (ReadGameError err)
        {
            System.out.println("Error reading file: " + err);
            return;
        }
        Game newGUI = JChessApp.jcv.addNewTab(whiteName + " vs. " + blackName);
        Settings locSetts = newGUI.settings;
        locSetts.playerBlack.name = blackName;
        locSetts.playerWhite.name = whiteName;
        locSetts.playerBlack.setType(Player.playerTypes.localUser);
        locSetts.playerWhite.setType(Player.playerTypes.localUser);
        locSetts.gameMode = Settings.gameModes.loadGame;
        locSetts.gameType = Settings.gameTypes.local;

        newGUI.newGame();
        newGUI.blockedChessboard = true;
        newGUI.moves.setMoves(tempStr);
        newGUI.blockedChessboard = false;
        newGUI.chessboard.repaint();
        //newGUI.chessboard.draw();
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
        //dirty hacks starts over here :) 
        //to fix rendering artefacts on first run
        Game activeGame = JChessApp.jcv.getActiveTabGame();
        if( activeGame != null && JChessApp.jcv.getNumberOfOpenedTabs() == 0 )
        {
            activeGame.chessboard.resizeChessboard(activeGame.chessboard.get_height(false));
            activeGame.chessboard.repaint();
            activeGame.repaint();
        }
        chessboard.repaint();
        this.repaint();
        //dirty hacks ends over here :)
    }

    /** Method to end game
     *  @param message what to show player(s) at end of the game (for example "draw", "black wins" etc.)
     */
    public void endGame(String massage)
    {
        this.blockedChessboard = true;
        System.out.println(massage);
        JOptionPane.showMessageDialog(null, massage);
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

    // MouseListener:
    public void mouseClicked(MouseEvent arg0)
    {
    }
    
    public boolean undo()
    {
        boolean status = false;
        
        if( this.settings.gameType == Settings.gameTypes.local )
        {
            status = chessboard.undo();
            if( status )
            {
                this.switchActive();
            }
            else
            {
                chessboard.repaint();//repaint for sure
            }
        }
        else if( this.settings.gameType == Settings.gameTypes.network )
        {
            this.client.sendUndoAsk();
            status = true;
        }
        return status;
    }
    
    public boolean rewindToBegin()
    {
        boolean result = false;
        
        if( this.settings.gameType == Settings.gameTypes.local )
        {
            while( chessboard.undo() )
            {
                result = true;
            }
        }
        else
        {
            throw new UnsupportedOperationException( Settings.lang("operation_supported_only_in_local_game") );
        }
        
        return result;
    }
    
    public boolean rewindToEnd() throws UnsupportedOperationException
    {
        boolean result = false;
        
        if( this.settings.gameType == Settings.gameTypes.local )
        {
            while( chessboard.redo() )
            {
                result = true;
            }
        }
        else
        {
            throw new UnsupportedOperationException( Settings.lang("operation_supported_only_in_local_game") );
        }
        
        return result;
    }
    
    public boolean redo()
    {
        boolean status = chessboard.redo();
        if( this.settings.gameType == Settings.gameTypes.local )
        {
            if ( status )
            {
                this.nextMove();
            }
            else
            {
                chessboard.repaint();//repaint for sure
            }
        }
        else
        {
            throw new UnsupportedOperationException( Settings.lang("operation_supported_only_in_local_game") );
        }
        return status;
    }
    
    

    public void mousePressed(MouseEvent event)
    {
        if (event.getButton() == MouseEvent.BUTTON3) //right button
        {
            this.undo();
        }
        else if (event.getButton() == MouseEvent.BUTTON2 && settings.gameType == Settings.gameTypes.local)
        {
            this.redo();
        }
        else if (event.getButton() == MouseEvent.BUTTON1) //left button
        {

            if (!blockedChessboard)
            {
                try 
                {
                    int x = event.getX();//get X position of mouse
                    int y = event.getY();//get Y position of mouse

                    Square sq = chessboard.getSquare(x, y);
                    if ((sq == null && sq.piece == null && chessboard.activeSquare == null)
                            || (this.chessboard.activeSquare == null && sq.piece != null && sq.piece.player != this.activePlayer))
                    {
                        return;
                    }

                    if (sq.piece != null && sq.piece.player == this.activePlayer && sq != chessboard.activeSquare)
                    {
                        chessboard.unselect();
                        chessboard.select(sq);
                    }
                    else if (chessboard.activeSquare == sq) //unselect
                    {
                        chessboard.unselect();
                    }
                    else if (chessboard.activeSquare != null && chessboard.activeSquare.piece != null
                            && chessboard.activeSquare.piece.allMoves().indexOf(sq) != -1) //move
                    {
                        if (settings.gameType == Settings.gameTypes.local)
                        {
                            chessboard.move(chessboard.activeSquare, sq);
                        }
                        else if (settings.gameType == Settings.gameTypes.network)
                        {
                            client.sendMove(chessboard.activeSquare.pozX, chessboard.activeSquare.pozY, sq.pozX, sq.pozY);
                            chessboard.move(chessboard.activeSquare, sq);
                        }

                        chessboard.unselect();

                        //switch player
                        this.nextMove();

                        //checkmate or stalemate
                        King king;
                        if (this.activePlayer == settings.playerWhite)
                        {
                            king = chessboard.kingWhite;
                        }
                        else
                        {
                            king = chessboard.kingBlack;
                        }

                        switch (king.isCheckmatedOrStalemated())
                        {
                            case 1:
                                this.endGame("Checkmate! " + king.player.color.toString() + " player lose!");
                                break;
                            case 2:
                                this.endGame("Stalemate! Draw!");
                                break;
                        }
                    }
                    
                } 
                catch(NullPointerException exc)
                {
                    System.err.println(exc.getMessage());
                    chessboard.repaint();
                    return;
                }
            }
            else if (blockedChessboard)
            {
                System.out.println("Chessboard is blocked");
            }
        }
        //chessboard.repaint();
    }

    public void mouseReleased(MouseEvent arg0)
    {
    }

    public void mouseEntered(MouseEvent arg0)
    {
    }

    public void mouseExited(MouseEvent arg0)
    {
    }

    public void componentResized(ComponentEvent e)
    {
        int height = this.getHeight() >= this.getWidth() ? this.getWidth() : this.getHeight();
        int chess_height = (int)Math.round( (height * 0.8)/8 )*8;
        this.chessboard.resizeChessboard((int)chess_height);
        chess_height = this.chessboard.getHeight();
        this.moves.getScrollPane().setLocation(new Point(chess_height + 5, 100));
        this.moves.getScrollPane().setSize(this.moves.getScrollPane().getWidth(), chess_height - 100);
        this.gameClock.setLocation(new Point(chess_height + 5, 0));
        if (this.chat != null)
        {
            this.chat.setLocation(new Point(0, chess_height + 5));
            this.chat.setSize(new Dimension(chess_height, this.getHeight() - (chess_height + 5))); 
        }
    }

    public void componentMoved(ComponentEvent e)
    {
    }

    public void componentShown(ComponentEvent e)
    {
    }

    public void componentHidden(ComponentEvent e)
    {
    }
}

class ReadGameError extends Exception
{
}