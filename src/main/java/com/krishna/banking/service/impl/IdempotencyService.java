package com.krishna.banking.service.impl;

import com.krishna.banking.entity.IdempotencyRecord;
import com.krishna.banking.entity.IdempotencyStatus;
import com.krishna.banking.repository.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final RedisTemplate<String,String> redisTemplate;
    final private IdempotencyRepository repository;

    public boolean acquire(String key){

        return Boolean.TRUE.equals(
                redisTemplate.opsForValue()
                        .setIfAbsent(
                                key,
                                "PROCESSING",
                                Duration.ofMinutes(5)
                        )
        );
    }

    public void success(
            String key,
            String referenceId){

        repository.save(
                IdempotencyRecord.builder()
                        .idempotencyKey(key)
                        .referenceId(referenceId)
                        .status(IdempotencyStatus.SUCCESS)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        redisTemplate.opsForValue().set(
                key,
                referenceId,
                Duration.ofHours(24)
        );
    }

    public void failed(String key){
        redisTemplate.delete(key);
    }
}