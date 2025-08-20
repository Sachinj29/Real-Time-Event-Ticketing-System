package com.example.ticketing.orders.web.dto;

import com.example.ticketing.orders.domain.OrderStatus;

public record OrderResponse(Long id, Long reservationId, Long amountCents, String currency, OrderStatus status,
                            String provider, String providerPaymentId) {}
