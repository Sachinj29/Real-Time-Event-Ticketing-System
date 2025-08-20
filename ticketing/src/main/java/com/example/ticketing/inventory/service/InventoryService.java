package com.example.ticketing.inventory.service;

import com.example.ticketing.inventory.repo.SeatInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final SeatInventoryRepository seatRepo;

    @Transactional
    public boolean lockSeats(Long eventId, List<Long> seatIds, int ttlSeconds) {
        Instant now = Instant.now();
        int updated = seatRepo.lockSeats(eventId, seatIds, now, now.plusSeconds(ttlSeconds));
        return updated == seatIds.size();
    }

    @Transactional
    public void releaseExpiredForSeats(Long eventId, List<Long> seatIds) {
        seatRepo.releaseExpiredLocks(eventId, seatIds, Instant.now());
    }

    @Transactional
    public int releaseAllExpired() {
        return seatRepo.releaseAllExpired(Instant.now());
    }

    @Transactional
    public void markSold(Long eventId, List<Long> seatIds) {
        seatRepo.markSold(eventId, seatIds);
    }
}
