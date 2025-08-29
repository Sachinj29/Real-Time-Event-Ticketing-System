package com.example.event_ticketing_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderResponse {
    private String orderId;
    private Double amount;
    private String currency;
    private String keyId;
    private UUID bookingId;
}