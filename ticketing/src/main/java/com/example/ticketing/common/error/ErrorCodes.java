package com.example.ticketing.common.error;

public final class ErrorCodes {
    private ErrorCodes() {}
    public static final String AUTH_INVALID_CREDENTIALS = "AUTH_INVALID_CREDENTIALS";
    public static final String AUTH_TOKEN_EXPIRED = "AUTH_TOKEN_EXPIRED";
    public static final String ACCESS_FORBIDDEN = "ACCESS_FORBIDDEN";
    public static final String EVENT_NOT_FOUND = "EVENT_NOT_FOUND";
    public static final String SEAT_CONFLICT = "SEAT_CONFLICT";
    public static final String RESERVATION_EXPIRED = "RESERVATION_EXPIRED";
    public static final String PAYMENT_REQUIRED = "PAYMENT_REQUIRED";
    public static final String PAYMENT_WEBHOOK_INVALID = "PAYMENT_WEBHOOK_INVALID";
    public static final String RATE_LIMITED = "RATE_LIMITED";
    public static final String VALIDATION_FAILED = "VALIDATION_FAILED";
}
