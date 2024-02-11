package com.concerting.auth.global.redis.repository;

import com.concerting.auth.global.security.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByRefreshToken(String refreshToken);
}
