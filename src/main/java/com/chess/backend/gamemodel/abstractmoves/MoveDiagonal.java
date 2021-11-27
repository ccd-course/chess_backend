package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.AbstractMove;
import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;

import java.util.HashSet;
import java.util.Set;

public class MoveDiagonal implements AbstractMove {
    private Game game;

    public MoveDiagonal(Game game) {
        this.game = game;
    }

    public Set<Move> concretise(){
        // TODO: Implement
        return new HashSet<>();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
