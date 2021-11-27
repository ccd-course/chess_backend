package com.chess.backend.board;

import com.chess.backend.pieces.Piece;

public class Square {
    private final int pozX;
    private final int pozY;
    private Piece piece;

    public Square(final int pozX,final int pozY){
        this.pozX = pozX;
        this.pozY = pozY;
    }

    public Square(final int pozX,final int pozY,Piece piece){
        this.pozX = pozX;
        this.pozY = pozY;
        this.piece = piece;
    }

    private void setPiece(Piece piece){
        this.piece = piece;
    }

    private Piece getPiece(){
        return this.piece;
    }

    private boolean isOccupied(){
        if(this.piece != null) return true;
        return false;
    }

    public String getPosition(){
        return this.pozX + "." + this.pozY;
    }

}
