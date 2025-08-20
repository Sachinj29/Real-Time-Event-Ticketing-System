package com.example.ticketing.inventory.web;

import com.example.ticketing.inventory.domain.Reservation;
import com.example.ticketing.inventory.service.InventoryService;
import com.example.ticketing.inventory.service.ReservationService;
import com.example.ticketing.inventory.web.dto.CreateReservationRequest;
import com.example.ticketing.inventory.web.dto.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final InventoryService inventory;
    private final ReservationService reservations;

    @Value("${app.reservations.ttl-seconds:300}")
    private int ttl;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@AuthenticationPrincipal Jwt jwt,
                                                      @Valid @RequestBody CreateReservationRequest req) {
        Long userId = Long.parseLong(jwt.getSubject());
        // Optionally release expired for these seats
        inventory.releaseExpiredForSeats(req.eventId(), req.seatIds());
        boolean locked = inventory.lockSeats(req.eventId(), req.seatIds(), ttl);
        if (!locked) {
            return ResponseEntity.status(409).build();
        }
        String itemsJson = "{\"seatIds\":" + req.seatIds().toString() + "}";
        Reservation r = reservations.create(userId, req.eventId(), itemsJson, ttl, req.idempotencyKey());
        return ResponseEntity.ok(new ReservationResponse(r.getId(), r.getEventId(), req.seatIds(), r.getStatus(), r.getReservedUntil()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable Long id) {
        Reservation r = reservations.findById(id).orElseThrow();
        // naive extraction for demo; in practice store normalized items
        List<Long> seatIds = List.of();
        return ResponseEntity.ok(new ReservationResponse(r.getId(), r.getEventId(), seatIds, r.getStatus(), r.getReservedUntil()));
    }
}
