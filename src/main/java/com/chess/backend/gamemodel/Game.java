package com.chess.backend.gamemodel;

import com.chess.backend.services.ChessboardService;
import lombok.Data;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
@Data
public class Game {
    public Chessboard chessboard;
    public Moves moves;
    private Player activePlayer;
    private int id;
    private Player[] players;


    /**
     * Method to switch active players after move
     */
    public void switchActive() {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(activePlayer)) {
                if (i == (players.length - 1)) {
                    activePlayer = players[0];
                    break;
                } else {
                    activePlayer = players[i+1];
                    break;
                }
            }
        }
    }

    public String getActivePlayerName(){
        return this.activePlayer.getName();
    }

    /**
     * Method to go to next move (checks if game is local/network etc.)
     */
    public void nextMove() {
        switchActive();
        System.out.println("next move, active player: " + activePlayer.getName() + ", color: " + activePlayer.getColor().name());
    }

    /**
     * Method to simulate Move to check if it's correct etc. (usable for network game).
     *
     * @param beginX from which X (on chessboard) move starts
     * @param beginY from which Y (on chessboard) move starts
     * @param endX   to   which X (on chessboard) move go
     * @param endY   to   which Y (on chessboard) move go
     */
    public boolean simulateMove(int beginX, int beginY, int endX, int endY) {
        try {
            if (chessboard.getSquares()[beginX][beginY].getPiece().getAllowedMoves(this).contains(chessboard.getSquares()[endX][endY])) //move
            {
                ChessboardService.move(chessboard, beginX, beginY, endX, endY);
            } else {
                System.out.println("Bad move");
                return false;
            }
            nextMove();

            return true;

        } catch (StringIndexOutOfBoundsException exc) {
            return false;
        } catch (ArrayIndexOutOfBoundsException exc) {
            return false;
        } catch (NullPointerException exc) {
            return false;
        } finally {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, "ERROR");
        }
    }
}