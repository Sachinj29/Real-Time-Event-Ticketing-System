package com.example.ticketing.events.web.dto;

import jakarta.validation.constraints.*;

import java.time.Instant;

public record UpdateTicketTypeRequest(
        @NotBlank String name,
        @NotNull @Positive Long priceCents,
        @NotBlank String currency,
        @NotNull @Positive Integer quota,
        Instant salesStart,
        Instant salesEnd,
        @PositiveOrZero Integer minPerOrder,
        @Positive Integer maxPerOrder
) {}
