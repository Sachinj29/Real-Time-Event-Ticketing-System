package com.example.ticketing.inventory.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateReservationRequest(
        @NotNull Long eventId,
        @NotNull List<Long> seatIds,
        String idempotencyKey
) {}
