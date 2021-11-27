package com.chess.backend.gamemodel;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private ArrayList<Piece> pieces;
    private int activePlayerID;

    public Game(ArrayList<Player> players, ArrayList<Piece> pieces, int activePlayerID) {
        this.players = players;
        this.pieces = pieces;
        this.activePlayerID = activePlayerID;
    }

    //void generatePieces(PlacementMapper placementMapper){
        // this.pieces = ...
    //}

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public int getActivePlayerID() {
        return activePlayerID;
    }

    public void setActivePlayerID(int activePlayerID) {
        this.activePlayerID = activePlayerID;
    }
}
