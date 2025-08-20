package com.example.ticketing.payments.razorpay.dto;

public record RazorpayWebhookPayload(String event, String payload, String signature) {}
