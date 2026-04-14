package com.krishna.banking.exception;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        System.out.println("Redis GET failed → fallback to DB");
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        System.out.println("Redis PUT failed → skipping cache");
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        System.out.println("Redis EVICT failed");
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        System.out.println("Redis CLEAR failed");
    }
}
