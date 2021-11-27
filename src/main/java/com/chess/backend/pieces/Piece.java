package com.chess.backend.pieces;

import com.chess.backend.board.Chessboard;
import com.chess.backend.board.Move;
import com.chess.backend.constants.COLOR;
import jchess.GUI;

import java.awt.*;
import java.util.List;

/*
    Abstract class for representing all pieces on the board
    Each piece should implement the method getAllPossibleMovements
    Which will return a list of all possible movements
*/
public abstract class Piece {
    private final COLOR color;
    public Chessboard chessboard;
    private Image image;
    private String pieceName;
    private int currentPosition;

    public Piece(final COLOR color,final Chessboard chessboard,String pieceName,int position){
        this.color = color; // Color of the piece
        this.chessboard = chessboard; // reference to the chessboard, in order to get all possible movements
        this.pieceName = pieceName; // name of the piece
        this.image = GUI.loadImage(this.pieceName +"-"+ this.color +".png"); // image of the piece
        this.currentPosition = position; // where the piece is placed
    }

    // Set the name
    // The pawn can be replaced, that's why setter is needed
    // The image will be changed also
    public void setPieceName(){
        this.pieceName = this.getClass().getSimpleName();
        this.image = GUI.loadImage(this.pieceName +"-"+ this.color +".png");
    }

    // Get the name
    public String getPieceName(){
        return this.pieceName;
    }

    // Get the color
    // It can no be changed, (No setter is needed)
    public COLOR getColor(){
        return this.color;
    }

    // Get the image
    public Image getImage(){
        return this.image;
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }

    public void setCurrentPosition(int currentPosition){
        this.currentPosition = currentPosition;
    }

    // Get a list of all possible movements
    // Should be implemented by the classes, which will inherit from this one
    public abstract List<Move> getAllPossibleMoves();

}
