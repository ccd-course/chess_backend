package com.chess.backend.gamemodel;

import com.chess.backend.services.ChessboardService;

import java.util.Objects;

/**
 * Represents a position (x and y coordinates).
 */
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

    /**
     * Returns the position left to the current position.
     * <p>
     * Left border can not be crossed.
     *
     * @param chessboard Chessboard context
     * @return Position left to the current position
     */
    public Position left(Chessboard chessboard) {
        if (this.getX() + 1 > ChessboardService.getMaxX(chessboard.getSquares())) {
            return null;
        } else {
            return new Position(this.getX() + 1, this.getY());
        }
    }

    /**
     * Returns the position right to the current position.
     * <p>
     * Right border can not be crossed.
     *
     * @param chessboard Chessboard context
     * @return Position right to the current position
     */
    public Position right(Chessboard chessboard) {
        if (this.getX() - 1 < 0) {
            return null;
        } else {
            return new Position(this.getX() - 1, this.getY());
        }
    }

    /**
     * Returns the position at the top of the current position.
     * <p>
     * In reality there is no top and bottom, because squares[][] represents a circle.
     * Therefore these must be linked.
     *
     * @param chessboard Chessboard context
     * @return Position at the top of the current position
     */
    public Position forward(Chessboard chessboard) {
        if (this.getY() - 1 < 0) {
            return new Position(this.getX(), ChessboardService.getMaxY(chessboard.getSquares()));
        } else {
            return new Position(this.getX(), this.getY() - 1);
        }
    }

    /**
     * Returns the position at the bottom of the current position.
     * <p>
     * In reality there is no top and bottom, because squares[][] represents a circle.
     * Therefore these must be linked.
     *
     * @param chessboard Chessboard context
     * @return Position at the bottom of the current position
     */
    public Position backward(Chessboard chessboard) {
        if (this.getY() + 1 > ChessboardService.getMaxY(chessboard.getSquares())) {
            return new Position(this.getX(), 0);
        } else {
            return new Position(this.getX(), this.getY() + 1);
        }
    }

    /**
     * Returns the position diagonally at the bottom left of the current position.
     * <p>
     * Composition of backwards/bottom and left.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the bottom left of the current position
     */
    public Position diagBL(Chessboard chessboard) {
        return this.backward(chessboard).left(chessboard);
    }

    /**
     * Returns the position diagonally at the bottom right of the current position.
     * <p>
     * Composition of backwards/bottom and right.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the bottom right of the current position
     */
    public Position diagBR(Chessboard chessboard) {
        return this.backward(chessboard).right(chessboard);
    }

    /**
     * Returns the position diagonally at the front left of the current position.
     * <p>
     * Composition of forward/top and left.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the front left of the current position
     */
    public Position diagFL(Chessboard chessboard) {
        return this.forward(chessboard).left(chessboard);
    }

    /**
     * Returns the position diagonally at the front right of the current position.
     * <p>
     * Composition of forward/top and right.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the front right of the current position
     */
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
