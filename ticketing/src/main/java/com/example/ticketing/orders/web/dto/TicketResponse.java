package com.example.ticketing.orders.web.dto;

public record TicketResponse(Long id, String ticketCode, Long eventId, String attendeeName, String attendeeEmail) {}
