package com.example.ticketing.venues.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVenueRequest(
        @NotBlank String name,
        String address,
        @NotBlank String timezone,
        String seatMapJson
) {}
