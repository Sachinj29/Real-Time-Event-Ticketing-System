package com.example.ticketing.events.web;

import com.example.ticketing.events.domain.TicketType;
import com.example.ticketing.events.service.EventService;
import com.example.ticketing.events.service.TicketTypeService;
import com.example.ticketing.events.web.dto.CreateTicketTypeRequest;
import com.example.ticketing.events.web.dto.UpdateTicketTypeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {

    private final TicketTypeService ticketTypes;
    private final EventService events;

    @PostMapping
    public ResponseEntity<TicketType> create(@PathVariable Long eventId, @Valid @RequestBody CreateTicketTypeRequest req) {
        events.get(eventId); // ensure event exists
        TicketType t = new TicketType();
        t.setEventId(eventId);
        t.setName(req.name());
        t.setPriceCents(req.priceCents());
        t.setCurrency(req.currency());
        t.setQuota(req.quota());
        t.setSalesStart(req.salesStart());
        t.setSalesEnd(req.salesEnd());
        if (req.minPerOrder() != null) t.setMinPerOrder(req.minPerOrder());
        if (req.maxPerOrder() != null) t.setMaxPerOrder(req.maxPerOrder());
        return ResponseEntity.ok(ticketTypes.create(t));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketType> update(@PathVariable Long eventId, @PathVariable Long id,
                                             @Valid @RequestBody UpdateTicketTypeRequest req) {
        events.get(eventId);
        TicketType t = new TicketType();
        t.setId(id);
        t.setEventId(eventId);
        t.setName(req.name());
        t.setPriceCents(req.priceCents());
        t.setCurrency(req.currency());
        t.setQuota(req.quota());
        t.setSalesStart(req.salesStart());
        t.setSalesEnd(req.salesEnd());
        t.setMinPerOrder(req.minPerOrder());
        t.setMaxPerOrder(req.maxPerOrder());
        return ResponseEntity.ok(ticketTypes.update(t));
    }

    @GetMapping
    public ResponseEntity<List<TicketType>> list(@PathVariable Long eventId) {
        events.get(eventId);
        return ResponseEntity.ok(ticketTypes.forEvent(eventId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long eventId, @PathVariable Long id) {
        events.get(eventId);
        ticketTypes.delete(id);
        return ResponseEntity.noContent().build();
    }
}
