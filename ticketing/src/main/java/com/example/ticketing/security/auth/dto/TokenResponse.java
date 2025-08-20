package com.example.ticketing.security.auth.dto;

public record TokenResponse(String accessToken, String refreshToken, String tokenType) {
    public static TokenResponse bearer(String access, String refresh) {
        return new TokenResponse(access, refresh, "Bearer");
    }
}
