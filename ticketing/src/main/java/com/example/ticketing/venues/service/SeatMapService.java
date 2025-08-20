package com.example.ticketing.venues.service;

import com.example.ticketing.venues.domain.Seat;
import com.example.ticketing.venues.repo.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatMapService {
    private final SeatRepository seats;

    public List<Seat> forVenue(Long venueId) {
        return seats.findByVenueId(venueId);
    }
}
