package com.gdscsolutionchallenge.shareBite.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public void saveAccessToken(String key, String accessToken, Long expiration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("ATK:" + key, accessToken, expiration, TimeUnit.MILLISECONDS);
    }

    public void saveRefreshToken(String key, String refreshToken, Long expiration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("RTK:" + key, refreshToken, expiration, TimeUnit.MILLISECONDS);
    }

    public Optional<String> getAccessToken(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get("ATK:" + key));
    }

    public Optional<String> getRefreshToken(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get("RTK:" + key));
    }

    public void deleteRefreshToken(String key) {
        redisTemplate.delete("RTK:" + key);
    }

    public void deleteAccessToken(String key) {
        redisTemplate.delete("ATK:" + key);
    }
}
