package com.example.event_ticketing_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
//public class SeatLockingService {
//
//    private final RedissonClient redissonClient;
//    private static final String SEAT_LOCK_PREFIX = "seat_lock:";
//    private static final long LOCK_WAIT_TIME = 10; // seconds
//    private static final long LOCK_LEASE_TIME = 300; // 5 minutes
//
//    public boolean lockSeats(UUID eventId, int seatsToBook, UUID userId) {
//        String lockKey = SEAT_LOCK_PREFIX + eventId + ":" + userId;
//        RLock lock = redissonClient.getLock(lockKey);
//
//        try {
//            boolean acquired = lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
//            if (acquired) {
//                log.info("Seat lock acquired for user {} on event {}, seats: {}", userId, eventId, seatsToBook);
//                return true;
//            } else {
//                log.warn("Failed to acquire seat lock for user {} on event {}", userId, eventId);
//                return false;
//            }
//        } catch (InterruptedException e) {
//            log.error("Lock acquisition interrupted for user {} on event {}", userId, eventId, e);
//            Thread.currentThread().interrupt();
//            return false;
//        }
//    }
//
//    public void releaseSeatLock(UUID eventId, UUID userId) {
//        String lockKey = SEAT_LOCK_PREFIX + eventId + ":" + userId;
//        RLock lock = redissonClient.getLock(lockKey);
//
//        if (lock.isHeldByCurrentThread()) {
//            lock.unlock();
//            log.info("Seat lock released for user {} on event {}", userId, eventId);
//        }
//    }
//
//    public boolean isSeatLocked(UUID eventId, UUID userId) {
//        String lockKey = SEAT_LOCK_PREFIX + eventId + ":" + userId;
//        RLock lock = redissonClient.getLock(lockKey);
//        return lock.isLocked();
//    }
//}


public class SeatLockingService {

    private final RedissonClient redissonClient; // You already have this

    public boolean lockSeats(UUID eventId, int ticketsCount, UUID userId) {
        String lockKey = "seat_lock:" + eventId + ":" + userId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // Try to acquire lock with 10 second wait, 300 second lease time
            boolean acquired = lock.tryLock(10, 300, TimeUnit.SECONDS);

            if (acquired) {
                log.info("✅ Lock acquired for user {} on event {}", userId, eventId);
                return true;
            } else {
                log.warn("❌ Failed to acquire lock for user {} on event {}", userId, eventId);
                return false;
            }
        } catch (InterruptedException e) {
            log.error("Lock acquisition interrupted", e);
            return false;
        }
    }

    public void releaseSeatLock(UUID eventId, UUID userId) {
        String lockKey = "seat_lock:" + eventId + ":" + userId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("🔓 Lock released for user {} on event {}", userId, eventId);
            }
        } catch (Exception e) {
            log.error("Error releasing lock", e);
        }
    }

    public void debugLockStatus(UUID eventId, UUID userId) {
        String lockKey = "seat_lock:" + eventId + ":" + userId;
        RLock lock = redissonClient.getLock(lockKey);

        log.info("Lock Debug - Key: {}", lockKey);
        log.info("Lock Debug - Is Locked: {}", lock.isLocked());
        log.info("Lock Debug - Is Held by Current Thread: {}", lock.isHeldByCurrentThread());
        log.info("Lock Debug - Remaining TTL: {}", lock.remainTimeToLive());
    }
}