package com.app.fineapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void save(String key, Object value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
    }

    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) return null;

        // если value уже правильного типа
        if (clazz.isInstance(value)) return clazz.cast(value);

        // если это LinkedHashMap — десериализуем вручную
        return objectMapper.convertValue(value, clazz);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}

