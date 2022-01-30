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
import lombok.Data;

/**
 * Represents a chessboard square.
 */
@Data
public class Square {
    private final Position position;
    private Piece piece = null;

    public Square(int posX, int posY, Piece piece) {
        this.position = new Position(posX, posY);
        this.piece = piece;
    }

    public void setPiece(Piece piece) {
        if (piece.getType() == PieceType.CANNON) {
            int a = 0;
        }

        piece.setPosX(this.getPosX());
        piece.setPosY(this.getPosY());
        this.piece = piece;
    }

    public PieceType getPieceTypeOfPiece(){
       if(this.piece!=null){
           return this.piece.getType();
       }
       else{
           return null;
       }
    }

    /**
     * Removes the piece from the square.
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
                "piece=" + piece +
                '}';
    }

    public boolean hasPiece(){
        return getPiece() != null;
    }
}
