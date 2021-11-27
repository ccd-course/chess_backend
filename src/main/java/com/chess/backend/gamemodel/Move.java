package com.chess.backend.gamemodel;

import java.util.ArrayList;

public class Move {
    private Position currentPosition;
    private Position newPosition;
    private Piece piece;
    private ArrayList<Position> steps;
    private boolean captureMove;

    public Move(Position currentPosition, Position newPosition, Piece piece, ArrayList<Position> steps, boolean captureMove) {
        this.currentPosition = currentPosition;
        this.newPosition = newPosition;
        this.piece = piece;
        this.steps = steps;
        this.captureMove = captureMove;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public ArrayList<Position> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Position> steps) {
        this.steps = steps;
    }

    public boolean isCaptureMove() {
        return captureMove;
    }

    public void setCaptureMove(boolean captureMove) {
        this.captureMove = captureMove;
    }
}
