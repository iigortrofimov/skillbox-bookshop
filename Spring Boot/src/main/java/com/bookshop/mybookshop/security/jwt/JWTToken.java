package com.bookshop.mybookshop.security.jwt;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RedisHash(value = "JWTToken")
public class JWTToken {

    @Id
    private UUID id = UUID.randomUUID();

    @EqualsAndHashCode.Include
    @Indexed
    private String token;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private long ttl;
}
