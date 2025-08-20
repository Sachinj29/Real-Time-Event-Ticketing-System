package com.example.ticketing.ratelimit;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRateLimiter {
    private static class Bucket {
        long tokens;
        long lastRefillEpochSec;
    }

    private final long capacity;
    private final long refillPerMinute;
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public InMemoryRateLimiter(long capacity, long refillPerMinute) {
        this.capacity = capacity;
        this.refillPerMinute = refillPerMinute;
    }

    public boolean allow(String key) {
        long nowSec = Instant.now().getEpochSecond();
        Bucket b = buckets.computeIfAbsent(key, k -> {
            Bucket nb = new Bucket();
            nb.tokens = capacity;
            nb.lastRefillEpochSec = nowSec;
            return nb;
        });
        refill(b, nowSec);
        if (b.tokens > 0) {
            b.tokens--;
            return true;
        }
        return false;
    }

    private void refill(Bucket b, long nowSec) {
        long elapsed = nowSec - b.lastRefillEpochSec;
        if (elapsed <= 0) return;
        long add = (refillPerMinute * elapsed) / 60;
        if (add > 0) {
            b.tokens = Math.min(capacity, b.tokens + add);
            b.lastRefillEpochSec = nowSec;
        }
    }
}
