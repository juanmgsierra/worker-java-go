package com.jmgs.worker.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedisService {
    
    private final StringRedisTemplate redisTemplate;

    public int incrementOrderRetry(String orderId) {

        String key = "order:retry:" + orderId;

        Long attempts = redisTemplate.opsForValue().increment(key);

        redisTemplate.expire(key, Duration.ofHours(1));

        return attempts.intValue();
    }

}
