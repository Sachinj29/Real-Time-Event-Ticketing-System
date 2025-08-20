package com.example.ticketing.payments.razorpay.dto;

public record RazorpayOrderResponse(String providerOrderId, Long amountCents, String currency) {}
