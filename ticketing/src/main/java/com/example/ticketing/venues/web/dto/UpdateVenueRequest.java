package com.example.ticketing.venues.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateVenueRequest(
        @NotBlank String name,
        String address,
        @NotBlank String timezone,
        String seatMapJson
) {}
