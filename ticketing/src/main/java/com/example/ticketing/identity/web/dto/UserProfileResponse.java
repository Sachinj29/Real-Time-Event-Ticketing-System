package com.example.ticketing.identity.web.dto;

public record UserProfileResponse(Long id, String email, String name, String roles, String status) {}
