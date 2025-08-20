package com.example.ticketing.orders.web.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull Long reservationId,
        @NotNull Long amountCents,
        @NotNull String currency
) {}
