package com.example.ticketing.payments.razorpay.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRazorpayOrderRequest(
        @NotNull Long orderId,
        @NotNull Long amountCents,
        @NotNull String currency
) {}
