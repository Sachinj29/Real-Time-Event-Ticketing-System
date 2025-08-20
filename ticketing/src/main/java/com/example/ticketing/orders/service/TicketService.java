package com.example.ticketing.orders.service;

import com.example.ticketing.orders.domain.Ticket;
import com.example.ticketing.orders.repo.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository tickets;

    public Ticket issue(Long orderId, Long eventId, String attendeeName, String attendeeEmail) {
        Ticket t = new Ticket();
        t.setOrderId(orderId);
        t.setEventId(eventId);
        t.setAttendeeName(attendeeName);
        t.setAttendeeEmail(attendeeEmail);
        t.setTicketCode("TKT-" + UUID.randomUUID());
        return tickets.save(t);
    }
}
