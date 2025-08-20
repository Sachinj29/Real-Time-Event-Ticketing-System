package com.example.ticketing.inventory.web.dto;

import java.time.Instant;
import java.util.List;

public record ReservationResponse(Long reservationId, Long eventId, List<Long> seatIds, String status, Instant reservedUntil) {}
