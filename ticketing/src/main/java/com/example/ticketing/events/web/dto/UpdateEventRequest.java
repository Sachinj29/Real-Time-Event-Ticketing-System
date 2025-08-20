package com.example.ticketing.events.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record UpdateEventRequest(
        @NotNull Long venueId,
        @NotBlank String name,
        String description,
        @NotNull Instant startAt,
        @NotNull Instant endAt,
        String status,
        String category,
        String imageUrl
) {}
