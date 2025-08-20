package com.example.ticketing.reviews.web.dto;

import jakarta.validation.constraints.*;

public record CreateReviewRequest(
        @NotNull Long eventId,
        @Min(1) @Max(5) int rating,
        @Size(max=2000) String text
) {}
