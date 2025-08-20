package com.example.ticketing.orders.web;

import com.example.ticketing.orders.domain.Ticket;
import com.example.ticketing.orders.repo.TicketRepository;
import com.example.ticketing.orders.web.dto.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketRepository tickets;

    @GetMapping("/{code}")
    public ResponseEntity<TicketResponse> find(@PathVariable String code) {
        Ticket t = tickets.findByTicketCode(code).orElseThrow();
        return ResponseEntity.ok(new TicketResponse(t.getId(), t.getTicketCode(), t.getEventId(), t.getAttendeeName(), t.getAttendeeEmail()));
    }
}
