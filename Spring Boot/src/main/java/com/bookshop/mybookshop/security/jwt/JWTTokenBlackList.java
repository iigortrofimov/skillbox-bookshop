package com.bookshop.mybookshop.security.jwt;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTTokenBlackList extends KeyValueRepository<JWTToken, UUID> {

    Optional<JWTToken> findByToken(String token);
}
