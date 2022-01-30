package com.chess.backend.gamemodel;

import com.chess.backend.domain.models.IGame;
import com.chess.backend.gamemodel.constants.GameMode;
import com.chess.backend.repository.metadata.EventObject;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import lombok.Data;

import java.util.List;

/**
 * Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
@Data
@IgnoreExtraProperties
public class ChessGame implements IGame {
    private Chessboard chessboard;
    private Moves moves;
    private Player activePlayer;
    private int id;
    private List<Player> players;
    private List<EventObject> events;
    private Player winner;
    private GameMode type;

}