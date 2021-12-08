package com.chess.backend.gamemodel;

import com.chess.backend.services.ChessboardService;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Left border can not be crossed
    public Position left(Chessboard chessboard) {
        if (this.getX() + 1 > ChessboardService.getMaxX(chessboard.getSquares())) {
            return null;
        } else {
            return new Position(this.getX() + 1, this.getY());
        }
    }

    // Right border can not be crossed
    public Position right(Chessboard chessboard) {
        if (this.getX() - 1 < 0) {
            return null;
        } else {
            return new Position(this.getX() - 1, this.getY());
        }
    }

    // In reality there is no top and bottom, because squares[][] represents a circle. Therefore these must be linked.
    public Position forward(Chessboard chessboard) {
        if (this.getY() - 1 < 0) {
            return new Position(this.getX(), ChessboardService.getMaxY(chessboard.getSquares()));
        } else {
            return new Position(this.getX(), this.getY() - 1);
        }
    }

    // In reality there is no top and bottom, because squares[][] represents a circle. Therefore these must be linked.
    public Position backward(Chessboard chessboard) {
        if (this.getY() + 1 > ChessboardService.getMaxY(chessboard.getSquares())) {
            return new Position(this.getX(), 0);
        } else {
            return new Position(this.getX(), this.getY() + 1);
        }
    }

    public Position diagBL(Chessboard chessboard) {
        return this.backward(chessboard).left(chessboard);
    }

    public Position diagBR(Chessboard chessboard) {
        return this.backward(chessboard).right(chessboard);
    }

    public Position diagFL(Chessboard chessboard) {
        return this.forward(chessboard).left(chessboard);
    }

    public Position diagFR(Chessboard chessboard) {
        return this.forward(chessboard).right(chessboard);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return getX() == position.getX()
                && getY() == position.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
