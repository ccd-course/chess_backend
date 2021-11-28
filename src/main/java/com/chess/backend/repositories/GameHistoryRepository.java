package com.chess.backend.repositories;

import com.chess.backend.models.GameHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameHistoryRepository extends MongoRepository<GameHistory, String> {
}
