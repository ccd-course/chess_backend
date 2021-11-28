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

import com.chess.backend.gamemodel.Moves.castling;
import com.chess.backend.gamemodel.contants.Color;
import com.chess.backend.gamemodel.contants.PieceType;

import java.awt.*;
import java.util.ArrayList;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squares of chessboard and sets the pieces(pawns)
 * which the owner is current player on it.
 */
public class Chessboard
{

    public static final int top = 0;
    public static final int bottom = 7;
    public Square squares[][];//squares of chessboard
    public Square activeSquare;
    private Image upDownLabel = null;
    private Image LeftRightLabel = null;
    private Point topLeft = new Point(0, 0);
    private int active_x_square;
    private int active_y_square;
    private float square_height;//height of square
    //public Graphics graph;
    private ArrayList moves;
    private Settings settings;
    public Piece kingWhite;
    public Piece kingBlack;
    //-------- for undo ----------
    private Square undo1_sq_begin = null;
    private Square undo1_sq_end = null;
    private Piece undo1_piece_begin = null;
    private Piece undo1_piece_end = null;
    private Piece ifWasEnPassant = null;
    private Piece ifWasCastling = null;
    private boolean breakCastling = false; //if last move break castling
    //----------------------------
    //For En passant:
    //|-> Pawn whose in last turn moved two square
    public Piece twoSquareMovedPawn = null;
    public Piece twoSquareMovedPawn2 = null;
    private Moves moves_history;

    /** Chessboard class constructor
     * @param settings reference to Settings class object for this chessboard
     * @param moves_history reference to Moves class object for this chessboard 
     */
    public Chessboard(Settings settings, Moves moves_history)
    {
        this.settings = settings;
        this.activeSquare = null;
        this.squares = new Square[8][8];//initalization of 8x8 chessboard
        this.active_x_square = 0;
        this.active_y_square = 0;
        for (int i = 0; i < 8; i++)
        {//create object for each square
            for (int y = 0; y < 8; y++)
            {
                this.squares[i][y] = new Square(i, y, null);
            }
        }//--endOf--create object for each square
        this.moves_history = moves_history;
    }/*--endOf-Chessboard--*/


    /** Method setPieces on begin of new game or loaded game
     * @param places string with pieces to set on chessboard
     * @param plWhite reference to white player
     * @param plBlack reference to black player
     */
    public void setPieces(String places, Player plWhite, Player plBlack)
    {

        if (places.equals("")) //if newGame
        {
            if (this.settings.upsideDown)
            {
                this.setPieces4NewGame(true, plWhite, plBlack);
            }
            else
            {
                this.setPieces4NewGame(false, plWhite, plBlack);
            }

        } 
        else //if loadedGame
        {
            return;
        }
    }/*--endOf-setPieces--*/


    /**
     *
     */
    private void setPieces4NewGame(boolean upsideDown, Player plWhite, Player plBlack)
    {

        /* WHITE PIECES */
        Player player = plBlack;
        Player player1 = plWhite;
        if (upsideDown) //if white on Top
        { 
            player = plWhite;
            player1 = plBlack;
        }
        this.setFigures4NewGame(0, player, upsideDown);
        this.setPawns4NewGame(1, player);
        this.setFigures4NewGame(7, player1, upsideDown);
        this.setPawns4NewGame(6, player1);
    }/*--endOf-setPieces(boolean upsideDown)--*/


    /**  method set Figures in row (and set Queen and King to right position)
     *  @param i row where to set figures (Rook, Knight etc.)
     *  @param player which is owner of pawns
     *  @param upsideDown if true white pieces will be on top of chessboard
     * */
    private void setFigures4NewGame(int i, Player player, boolean upsideDown)
    {

        if (i != 0 && i != 7)
        {
            System.out.println("error setting figures like rook etc.");
            return;
        }
        else if (i == 0)
        {
            player.goDown = true;
        }

        this.squares[0][i].setPiece(new Piece(PieceType.ROOK, player.getColor()));
        this.squares[7][i].setPiece(new Piece(PieceType.ROOK, player.getColor()));
        this.squares[1][i].setPiece(new Piece(PieceType.KNIGHT, player.getColor()));
        this.squares[6][i].setPiece(new Piece(PieceType.KNIGHT, player.getColor()));
        this.squares[2][i].setPiece(new Piece(PieceType.BISHOP, player.getColor()));
        this.squares[5][i].setPiece(new Piece(PieceType.BISHOP, player.getColor()));
        if (upsideDown)
        {
            this.squares[4][i].setPiece(new Piece(PieceType.QUEEN, player.getColor()));
            if (player.color == Color.WHITE)
            {
                this.squares[3][i].setPiece(kingWhite = new Piece(PieceType.KING, player.getColor()));
            }
            else
            {
                this.squares[3][i].setPiece(kingBlack = new Piece(PieceType.KING, player.getColor()));
            }
        }
        else
        {
            this.squares[3][i].setPiece(new Piece(PieceType.QUEEN, player.getColor()));
            if (player.color == Color.WHITE)
            {
                this.squares[4][i].setPiece(kingWhite = new Piece(PieceType.KING, player.getColor()));
            }
            else
            {
                this.squares[4][i].setPiece(kingBlack = new Piece(PieceType.KING, player.getColor()));
            }
        }
    }

    /**  method set Pawns in row
     *  @param i row where to set pawns
     *  @param player player which is owner of pawns
     * */
    private void setPawns4NewGame(int i, Player player)
    {
        if (i != 1 && i != 6)
        {
            System.out.println("error setting pawns etc.");
            return;
        }
        for (int x = 0; x < 8; x++)
        {
            this.squares[x][i].setPiece(new Piece(PieceType.PAWN, player.getColor()));
        }
    }

    /** Method selecting piece in chessboard
     * @param  sq square to select (when clicked))
     */
    public void select(Square sq)
    {
        this.activeSquare = sq;
        this.active_x_square = sq.pozX + 1;
        this.active_y_square = sq.pozY + 1;
    }

    /** Method set variables active_x_square & active_y_square
     * to 0 values.
     */
    public void unselect()
    {
        this.active_x_square = 0;
        this.active_y_square = 0;
        this.activeSquare = null;
    }

    public void move(Square begin, Square end)
    {
        move(begin, end, true);
    }

    /** Method to move piece over chessboard
     * @param xFrom from which x move piece
     * @param yFrom from which y move piece
     * @param xTo to which x move piece
     * @param yTo to which y move piece
     */
    public void move(int xFrom, int yFrom, int xTo, int yTo)
    {
        Square fromSQ = null;
        Square toSQ = null;
        try
        {
            fromSQ = this.squares[xFrom][yFrom];
            toSQ = this.squares[xTo][yTo];
        }
        catch (IndexOutOfBoundsException exc)
        {
            System.out.println("error moving piece: " + exc);
            return;
        }
        this.move(this.squares[xFrom][yFrom], this.squares[xTo][yTo], true);
    }

    public void move(Square begin, Square end, boolean refresh)
    {
        this.move(begin, end, refresh, true);
    }

    /** Method move piece from square to square
     * @param begin square from which move piece
     * @param end square where we want to move piece         *
     * @param refresh chessboard, default: true
     * */
    public void move(Square begin, Square end, boolean refresh, boolean clearForwardHistory)
    {

        castling wasCastling = castling.none;
        Piece promotedPiece = null;
        boolean wasEnPassant = false;
        if (end.piece != null)
        {
            end.piece.square = null;
        }

        Square tempBegin = new Square(begin);//4 moves history
        Square tempEnd = new Square(end);  //4 moves history
        //for undo
        undo1_piece_begin = begin.piece;
        undo1_sq_begin = begin;
        undo1_piece_end = end.piece;
        undo1_sq_end = end;
        ifWasEnPassant = null;
        ifWasCastling = null;
        breakCastling = false;
        // ---

        twoSquareMovedPawn2 = twoSquareMovedPawn;

        begin.piece.square = end;//set square of piece to ending
        end.piece = begin.piece;//for ending square set piece from beginin square
        begin.piece = null;//make null piece for begining square

        if (end.piece.getType().equals(PieceType.KING))
        {
            if (!end.piece.isMotioned())
            {
                breakCastling = true;
                end.piece.setMotioned(true);
            }

            //Castling
            if (begin.pozX + 2 == end.pozX)
            {
                move(squares[7][begin.pozY], squares[end.pozX - 1][begin.pozY], false, false);
                ifWasCastling = end.piece;  //for undo
                wasCastling = castling.shortCastling;
                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
                //return;
            }
            else if (begin.pozX - 2 == end.pozX)
            {
                move(squares[0][begin.pozY], squares[end.pozX + 1][begin.pozY], false, false);
                ifWasCastling = end.piece;  // for undo
                wasCastling = castling.longCastling;
                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
                //return;
            }
            //endOf Castling
        }
        else if (end.piece.getType().equals(PieceType.ROOK))
        {
            if (!end.piece.isMotioned())
            {
                breakCastling = true;
                end.piece.setMotioned(true);
            }
        }
        else if (end.piece.getType().equals(PieceType.PAWN))
        {
            if (twoSquareMovedPawn != null && squares[end.pozX][begin.pozY] == twoSquareMovedPawn.square) //en passant
            {
                ifWasEnPassant = squares[end.pozX][begin.pozY].piece; //for undo

                tempEnd.piece = squares[end.pozX][begin.pozY].piece; //ugly hack - put taken pawn in en passant plasty do end square

                squares[end.pozX][begin.pozY].piece = null;
                wasEnPassant = true;
            }

            if (begin.pozY - end.pozY == 2 || end.pozY - begin.pozY == 2) //moved two square
            {
                breakCastling = true;
                twoSquareMovedPawn = end.piece;
            }
            else
            {
                twoSquareMovedPawn = null; //erase last saved move (for En passant)
            }

            if (end.piece.square.pozY == 0 || end.piece.square.pozY == 7) //promote Pawn
            {
                if (clearForwardHistory)
                {
                    String color;
                    if (end.piece.player.color == Color.WHITE)
                    {
                        color = "W"; // promotionWindow was show with pieces in this color
                    }
                    else
                    {
                        color = "B";
                    }

                    // TODO: Get new piece type from frontend
                    String newPiece = "Queen"; // JChessApp.jcv.showPawnPromotionBox(color); //return name of new piece

                    if (newPiece.equals("Queen")) // transform pawn to queen
                    {
                        Piece queen = new Piece(PieceType.QUEEN, end.piece.player.getColor());
                        queen.chessboard = end.piece.chessboard;
                        queen.player = end.piece.player;
                        queen.square = end.piece.square;
                        end.piece = queen;
                    }
                    else if (newPiece.equals("Rook")) // transform pawn to rook
                    {
                        Piece rook = new Piece(PieceType.ROOK, end.piece.player.getColor());
                        rook.chessboard = end.piece.chessboard;
                        rook.player = end.piece.player;
                        rook.square = end.piece.square;
                        end.piece = rook;
                    }
                    else if (newPiece.equals("Bishop")) // transform pawn to bishop
                    {
                        Piece bishop = new Piece(PieceType.BISHOP, end.piece.player.getColor());
                        bishop.chessboard = end.piece.chessboard;
                        bishop.player = end.piece.player;
                        bishop.square = end.piece.square;
                        end.piece = bishop;
                    }
                    else // transform pawn to knight
                    {
                        Piece knight = new Piece(PieceType.KNIGHT, end.piece.player.getColor());
                        knight.chessboard = end.piece.chessboard;
                        knight.player = end.piece.player;
                        knight.square = end.piece.square;
                        end.piece = knight;
                    }
                    promotedPiece = end.piece;
                }
            }
        }
        else if (!end.piece.getType().equals(PieceType.PAWN))
        {
            twoSquareMovedPawn = null; //erase last saved move (for En passant)
        }
        //}

        // TODO: Controller may not trigger repaint
        if (refresh)
        {
            this.unselect();//unselect square
            // repaint();
        }

        if (clearForwardHistory)
        {
            this.moves_history.clearMoveForwardStack();
            this.moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
        }
        else
        {
            this.moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
        }
    }/*endOf-move()-*/


    public boolean redo()
    {
        return redo(true);
    }

    public boolean redo(boolean refresh)
    {
        if ( this.settings.gameType == Settings.gameTypes.local ) //redo only for local game
        {
            Move first = this.moves_history.redo();

            Square from = null;
            Square to = null;

            if (first != null)
            {
                from = first.getFrom();
                to = first.getTo();

                this.move(this.squares[from.pozX][from.pozY], this.squares[to.pozX][to.pozY], true, false);
                if (first.getPromotedPiece() != null)
                {
                    Piece pawn = this.squares[to.pozX][to.pozY].piece;
                    pawn.square = null;

                    this.squares[to.pozX][to.pozY].piece = first.getPromotedPiece();
                    Piece promoted = this.squares[to.pozX][to.pozY].piece;
                    promoted.square = this.squares[to.pozX][to.pozY];
                }
                return true;
            }
            
        }
        return false;
    }

    public boolean undo()
    {
        return undo(true);
    }

    public synchronized boolean undo(boolean refresh) //undo last move
    {
        Move last = this.moves_history.undo();


        if (last != null && last.getFrom() != null)
        {
            Square begin = last.getFrom();
            Square end = last.getTo();
            try
            {
                Piece moved = last.getMovedPiece();
                this.squares[begin.pozX][begin.pozY].piece = moved;

                moved.square = this.squares[begin.pozX][begin.pozY];

                Piece taken = last.getTakenPiece();
                if (last.getCastlingMove() != castling.none)
                {
                    Piece rook = null;
                    if (last.getCastlingMove() == castling.shortCastling)
                    {
                        rook = this.squares[end.pozX - 1][end.pozY].piece;
                        this.squares[7][begin.pozY].piece = rook;
                        rook.square = this.squares[7][begin.pozY];
                        this.squares[end.pozX - 1][end.pozY].piece = null;
                    }
                    else
                    {
                        rook = this.squares[end.pozX + 1][end.pozY].piece;
                        this.squares[0][begin.pozY].piece = rook;
                        rook.square = this.squares[0][begin.pozY];
                        this.squares[end.pozX + 1][end.pozY].piece = null;
                    }
                    moved.setMotioned(false);
                    rook.setMotioned(false);
                    this.breakCastling = false;
                }
                else if (moved.getType().equals(PieceType.ROOK))
                {
                    moved.setMotioned(false);
                }
                else if (moved.getType().equals(PieceType.PAWN) && last.wasEnPassant())
                {
                    Piece pawn = last.getTakenPiece();
                    this.squares[end.pozX][begin.pozY].piece = pawn;
                    pawn.square = this.squares[end.pozX][begin.pozY];

                }
                else if (moved.getType().equals(PieceType.PAWN) && last.getPromotedPiece() != null)
                {
                    Piece promoted = this.squares[end.pozX][end.pozY].piece;
                    promoted.square = null;
                    this.squares[end.pozX][end.pozY].piece = null;
                }

                //check one more move back for en passant
                Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
                if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove())
                {
                    Piece canBeTakenEnPassant = this.squares[oneMoveEarlier.getTo().pozX][oneMoveEarlier.getTo().pozY].piece;
                    if (canBeTakenEnPassant.getType().equals(PieceType.PAWN))
                    {
                        this.twoSquareMovedPawn = canBeTakenEnPassant;
                    }
                }

                if (taken != null && !last.wasEnPassant())
                {
                    this.squares[end.pozX][end.pozY].piece = taken;
                    taken.square = this.squares[end.pozX][end.pozY];
                }
                else
                {
                    this.squares[end.pozX][end.pozY].piece = null;
                }

                // TODO: Controller may not trigger repaint
                if (refresh)
                {
                    this.unselect();//unselect square
                    // repaint();
                }

            }
            catch (ArrayIndexOutOfBoundsException exc)
            {
                return false;
            }
            catch (NullPointerException exc)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
