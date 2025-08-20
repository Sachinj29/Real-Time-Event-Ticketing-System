package com.example.ticketing.inventory.web.dto;

import com.example.ticketing.inventory.domain.InventoryStatus;

public record AvailabilitySnapshot(Long seatId, InventoryStatus status, Long eventId) {}
