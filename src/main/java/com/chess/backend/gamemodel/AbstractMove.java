package com.chess.backend.gamemodel;

import java.util.Set;

public interface AbstractMove {
    abstract Set<Move> concretise();
}
