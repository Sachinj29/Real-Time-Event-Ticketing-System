package com.example.ticketing.reviews.web.dto;

import jakarta.validation.constraints.*;

public record UpdateReviewRequest(
        @Min(1) @Max(5) int rating,
        @Size(max=2000) String text
) {}
