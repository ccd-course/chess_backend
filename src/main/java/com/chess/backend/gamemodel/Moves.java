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

import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;
import com.chess.backend.services.ChessboardService;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class representing the players moves history.
 * <p>
 * It also checks that the moves taken by the player are correct.
 * All moves which were made by the current player saved as a list of strings.
 */
public class Moves {

    protected Stack<Move> moveBackStack = new Stack<Move>();
    protected Stack<Move> moveForwardStack = new Stack<Move>();
    private final ArrayList<String> move = new ArrayList<String>();
    private final int columnsNum = 3;
    private int rowsNum = 0;
    private final String[] names = new String[]
            {
                    Settings.lang("white"), Settings.lang("black")
            };
    private boolean enterBlack = false;
    private final Game game;

    Moves(Game game) {
        super();
        this.game = game;
    }

    /**
     * Method with is checking is the move is correct
     *
     * @param move String which in is capt player move
     * @return boolean 1 if the move is correct, else 0
     */
    static public boolean isMoveCorrect(String move) {
        if (move.equals("O-O") || move.equals("O-O-O")) {
            return true;
        }
        try {
            int from = 0;
            int sign = move.charAt(from);//get First
            switch (sign)  //if sign of piece, get next
            {
                case 66: // B like Bishop
                case 75: // K like King
                case 78: // N like Knight
                case 81: // Q like Queen
                case 82:
                    from = 1;
                    break; // R like Rook
            }
            sign = move.charAt(from);
            System.out.println(sign);
            if (sign < 97 || sign > 104) //if lower than 'a' or higher than 'h'
            {
                return false;
            }
            sign = move.charAt(from + 1);
            if (sign < 49 || sign > 56) //if lower than '1' or higher than '8'
            {
                return false;
            }
            if (move.length() > 3) //if is equal to 3 or lower, than it's in short notation, no more checking needed
            {
                sign = move.charAt(from + 2);
                if (sign != 45 && sign != 120) //if isn't '-' and 'x'
                {
                    return false;
                }
                sign = move.charAt(from + 3);
                if (sign < 97 || sign > 104) //if lower than 'a' or higher than 'h'
                {
                    return false;
                }
                sign = move.charAt(from + 4);
                if (sign < 49 || sign > 56) //if lower than '1' or higher than '8'
                {
                    return false;
                }
            }
        } catch (StringIndexOutOfBoundsException exc) {
            return false;
        }

        return true;
    }

    protected void addCastling(String move) {
        this.move.remove(this.move.size() - 1);//remove last element (move of Rook)
        this.move.add(move);//add new move (O-O or O-O-O)
    }

    /**
     * Method of adding new move
     *
     * @param move String which in is capt player move
     */
    public void addMove(String move) {
        if (isMoveCorrect(move)) {
            this.move.add(move);
            this.moveForwardStack.clear();
        }

    }

    public void addMove(Square begin, Square end, boolean registerInHistory, castling castlingMove, boolean wasEnPassant, Piece promotedPiece) {
        boolean wasCastling = castlingMove != castling.none;
        String locMove = begin.getPiece().getType().symbol;

        if (game.settings.upsideDown) {
            locMove += Character.toString((char) ((ChessboardService.getBottom(game.chessboard.getSquares()) - begin.getPosX()) + 97));//add letter of Square from which move was made
            locMove += Integer.toString(begin.getPosY() + 1);//add number of Square from which move was made
        } else {
            locMove += Character.toString((char) (begin.getPosX() + 97));//add letter of Square from which move was made
            locMove += Integer.toString(8 - begin.getPosY());//add number of Square from which move was made
        }

        if (end.getPiece() != null) {
            locMove += "x";//take down opponent piece
        } else {
            locMove += "-";//normal move
        }

        if (game.settings.upsideDown) {
            locMove += Character.toString((char) ((ChessboardService.getBottom(game.chessboard.getSquares()) - end.getPosX()) + 97));//add letter of Square to which move was made
            locMove += Integer.toString(end.getPosY() + 1);//add number of Square to which move was made
        } else {
            locMove += Character.toString((char) (end.getPosX() + 97));//add letter of Square to which move was made
            locMove += Integer.toString(8 - end.getPosY());//add number of Square to which move was made
        }

        if (begin.getPiece().getType().symbol.equals("") && begin.getPosX() - end.getPosX() != 0 && end.getPiece() == null) {
            locMove += "(e.p)";//pawn take down opponent en passant
            wasEnPassant = true;
        }
        if ((!this.enterBlack && ChessboardService.searchSquaresByPiece(this.game.chessboard.squares, PieceType.KING, Color.BLACK, null).get(0).getPiece().isChecked())
                || (this.enterBlack && ChessboardService.searchSquaresByPiece(this.game.chessboard.squares, PieceType.KING, Color.WHITE, null).get(0).getPiece().isChecked())) {//if checked

            if ((!this.enterBlack && ChessboardService.searchSquaresByPiece(this.game.chessboard.squares, PieceType.KING, Color.BLACK, null).get(0).getPiece().isCheckmatedOrStalemated(this.game) == 1)
                    || (this.enterBlack && ChessboardService.searchSquaresByPiece(this.game.chessboard.squares, PieceType.KING, Color.WHITE, null).get(0).getPiece().isCheckmatedOrStalemated(this.game) == 1)) {//check if checkmated
                locMove += "#";//check mate
            } else {
                locMove += "+";//check
            }
        }
        if (castlingMove == castling.shortCastling) {
            this.addCastling("0-0");
        } else if (castlingMove == castling.longCastling) {
            this.addCastling("0-0-0");
        } else {
            this.move.add(locMove);
        }

        if (registerInHistory) {
            this.moveBackStack.add(new Move(new Square(begin), new Square(end), begin.getPiece(), end.getPiece(), castlingMove, wasEnPassant, promotedPiece));
        }
    }

    public void clearMoveForwardStack() {
        this.moveForwardStack.clear();
    }

    public ArrayList<String> getMoves() {
        return this.move;
    }

    /**
     * Method to set all moves from String with validation test (useful for network game)
     *
     * @param moves String to set in String like PGN with full-notation format
     */
    public void setMoves(String moves) {
        int from = 0;
        int to = 0;
        int n = 1;
        ArrayList<String> tempArray = new ArrayList();
        int tempStrSize = moves.length() - 1;
        while (true) {
            from = moves.indexOf(" ", from);
            to = moves.indexOf(" ", from + 1);
            //System.out.println(from+">"+to);
            try {
                tempArray.add(moves.substring(from + 1, to).trim());
            } catch (StringIndexOutOfBoundsException exc) {
                System.out.println("error parsing file to load: " + exc);
                break;
            }
            if (n % 2 == 0) {
                from = moves.indexOf(".", to);
                if (from < to) {
                    break;
                }
            } else {
                from = to;
            }
            n += 1;
            if (from > tempStrSize || to > tempStrSize) {
                break;
            }
        }
        for (String locMove : tempArray) //test if moves are written correctly
        {
            if (!Moves.isMoveCorrect(locMove.trim())) //if not
            {
                // TODO: Send message to frontend?
                // JOptionPane.showMessageDialog(this.game, Settings.lang("invalid_file_to_load") + move);
                return;//show message and finish reading game
            }
        }
        boolean canMove = false;
        for (String locMove : tempArray) {
            if (locMove.equals("O-O-O") || locMove.equals("O-O")) //if castling
            {
                int[] values = new int[4];
                if (locMove.equals("O-O-O")) {
                    if (this.game.getActivePlayer().color == Color.BLACK) //if black turn
                    {
                        values = new int[]
                                {
                                        4, 0, 2, 0
                                };//move value for castling (King move)
                    } else {
                        values = new int[]
                                {
                                        4, 7, 2, 7
                                };//move value for castling (King move)
                    }
                } else if (locMove.equals("O-O")) //if short castling
                {
                    if (this.game.getActivePlayer().getColor() == Color.BLACK) //if black turn
                    {
                        values = new int[]
                                {
                                        4, 0, 6, 0
                                };//move value for castling (King move)
                    } else {
                        values = new int[]
                                {
                                        4, 7, 6, 7
                                };//move value for castling (King move)
                    }
                }
                canMove = this.game.simulateMove(values[0], values[1], values[2], values[3]);

                if (!canMove) //if move is illegal
                {
                    // TODO: Send message to frontend about illegal move
                    // JOptionPane.showMessageDialog(this.game, Settings.lang("illegal_move_on") + locMove);
                    return;//finish reading game and show message
                }
                continue;
            }
            from = 0;
            int num = locMove.charAt(from);
            if (num <= 90 && num >= 65) {
                from = 1;
            }
            int xFrom = 9; //set to higher value than chessboard has fields, to cause error if piece won't be found
            int yFrom = 9;
            int xTo = 9;
            int yTo = 9;
            boolean pieceFound = false;
            if (locMove.length() <= 3) {
                Square[][] squares = this.game.chessboard.squares;
                xTo = locMove.charAt(from) - 97;//from ASCII
                yTo = ChessboardService.getBottom(game.chessboard.getSquares()) - (locMove.charAt(from + 1) - 49);//from ASCII
                for (int i = 0; i < squares.length && !pieceFound; i++) {
                    for (int j = 0; j < squares[i].length && !pieceFound; j++) {
                        if (squares[i][j].getPiece() == null || this.game.getActivePlayer().color != squares[i][j].getPiece().player.color) {
                            continue;
                        }
                        ArrayList pieceMoves = squares[i][j].getPiece().getAllowedMoves(game);
                        for (Object square : pieceMoves) {
                            Square currSquare = (Square) square;
                            if (currSquare.getPosX() == xTo && currSquare.getPosY() == yTo) {
                                xFrom = squares[i][j].getPiece().square.getPosX();
                                yFrom = squares[i][j].getPiece().square.getPosY();
                                pieceFound = true;
                            }
                        }
                    }
                }
            } else {
                xFrom = locMove.charAt(from) - 97;//from ASCII
                yFrom = ChessboardService.getBottom(game.chessboard.getSquares()) - (locMove.charAt(from + 1) - 49);//from ASCII
                xTo = locMove.charAt(from + 3) - 97;//from ASCII
                yTo = ChessboardService.getBottom(game.chessboard.getSquares()) - (locMove.charAt(from + 4) - 49);//from ASCII
            }
            canMove = this.game.simulateMove(xFrom, yFrom, xTo, yTo);
            if (!canMove) //if move is illegal
            {
                // TODO: Send message to frontend about illegal move
                // JOptionPane.showMessageDialog(this.game, Settings.lang("illegal_move_on") + locMove);
                return;//finish reading game and show message
            }
        }
    }

    public synchronized Move getLastMoveFromHistory() {
        try {
            Move last = this.moveBackStack.get(this.moveBackStack.size() - 1);
            return last;
        } catch (ArrayIndexOutOfBoundsException exc) {
            return null;
        }
    }

    public synchronized Move getNextMoveFromHistory() {
        try {
            Move next = this.moveForwardStack.get(this.moveForwardStack.size() - 1);
            return next;
        } catch (ArrayIndexOutOfBoundsException exc) {
            return null;
        }

    }

    public synchronized Move undo() {
        try {
            Move last = this.moveBackStack.pop();
            if (last != null) {
                if (this.game.settings.gameType == Settings.gameTypes.local) //moveForward / redo available only for local game
                {
                    this.moveForwardStack.push(last);
                }
                if (this.enterBlack) {
                    if (this.rowsNum > 0) {
                        this.rowsNum--;
                    }
                }
                this.move.remove(this.move.size() - 1);
                this.enterBlack = !this.enterBlack;
            }
            return last;
        } catch (java.util.EmptyStackException exc) {
            this.enterBlack = false;
            return null;
        } catch (ArrayIndexOutOfBoundsException exc) {
            return null;
        }
    }

    public synchronized Move redo() {
        try {
            if (this.game.settings.gameType == Settings.gameTypes.local) {
                Move first = this.moveForwardStack.pop();
                this.moveBackStack.push(first);

                return first;
            }
            return null;
        } catch (java.util.EmptyStackException exc) {
            return null;
        }

    }

    public void addMoves(ArrayList<String> list) {
        for (String singleMove : list) {
            if (isMoveCorrect(singleMove)) {
                this.addMove(singleMove);
            }
        }
    }

    /**
     * Method of getting the moves in string
     *
     * @return str String which in is capt player move
     */
    public String getMovesInString() {
        int n = 1;
        int i = 0;
        String str = "";
        for (String locMove : this.getMoves()) {
            if (i % 2 == 0) {
                str += n + ". ";
                n += 1;
            }
            str += locMove + " ";
            i += 1;
        }
        return str;
    }

    enum castling {
        none, shortCastling, longCastling
    }
}
