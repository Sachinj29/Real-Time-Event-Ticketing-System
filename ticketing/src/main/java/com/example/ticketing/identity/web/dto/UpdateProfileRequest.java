package com.example.ticketing.identity.web.dto;

import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(@Size(max=100) String name) {}
