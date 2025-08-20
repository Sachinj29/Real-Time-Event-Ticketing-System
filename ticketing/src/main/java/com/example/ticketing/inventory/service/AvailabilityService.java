package com.example.ticketing.inventory.service;

import com.example.ticketing.inventory.domain.SeatInventory;
import com.example.ticketing.inventory.repo.SeatInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final SeatInventoryRepository repo;

    public List<SeatInventory> snapshotBySeats(Long eventId, List<Long> seatIds) {
        return repo.findByEventIdAndSeatIdIn(eventId, seatIds);
    }
}
