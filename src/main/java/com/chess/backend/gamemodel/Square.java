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


import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.domain.models.IPiece;

/**
 * Represents a chessboard square.
 */
public class Square {
    private final Position position;
    /**
     * Piece on a square
     */
    private IPiece piece = null;

    public Square(int posX, int posY, IPiece piece) {
        this.position = new Position(posX, posY);
        this.piece = piece;
    }

    Square(Square square) {
        this.position = square.getPos();
        this.piece = square.getPiece();
    }

    public IPiece getPiece() {
        return piece;
    }

    public void setPiece(IPiece piece) {
        this.piece = piece;
//        this.piece.setSquare(this);
    }

    public PieceType getPieceTypeOfPiece(){
        return piece.getType();
    }

    /**
     * Removes the piece from tha square.
     */
    public void removePiece() {
        this.piece = null;
    }

    public int getPosX() {
        return position.getX();
    }

    public int getPosY() {
        return position.getY();
    }

    public Position getPos() {
        return position;
    }

    @Override
    public String toString() {
        return "Square{" +
                "position=" + position +
                '}';
    }

    public boolean hasPiece(){
        if(getPiece() == null){
            return false;
        } else {
            return true;
        }
    }
}
