package com.epiaggregator.services.auth.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {
    Token findOneByToken(String token);
}
