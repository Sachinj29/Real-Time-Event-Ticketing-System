package com.example.ticketing.inventory.web;

import com.example.ticketing.inventory.domain.SeatInventory;
import com.example.ticketing.inventory.service.AvailabilityService;
import com.example.ticketing.inventory.web.dto.AvailabilitySnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/{eventId}")
    public List<AvailabilitySnapshot> snapshot(@PathVariable Long eventId, @RequestParam List<Long> seatIds) {
        List<SeatInventory> list = availabilityService.snapshotBySeats(eventId, seatIds);
        return list.stream().map(si -> new AvailabilitySnapshot(si.getSeatId(), si.getStatus(), si.getEventId())).toList();
    }
}
