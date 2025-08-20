package com.example.ticketing.events.web;

import com.example.ticketing.events.domain.Event;
import com.example.ticketing.events.service.EventService;
import com.example.ticketing.events.web.dto.CreateEventRequest;
import com.example.ticketing.events.web.dto.UpdateEventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> create(@Valid @RequestBody CreateEventRequest req) {
        Event e = new Event();
        e.setOrganizerId(req.organizerId());
        e.setVenueId(req.venueId());
        e.setName(req.name());
        e.setDescription(req.description());
        e.setStartAt(req.startAt());
        e.setEndAt(req.endAt());
        e.setCategory(req.category());
        e.setImageUrl(req.imageUrl());
        e.setStatus("DRAFT");
        return ResponseEntity.ok(eventService.create(e));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @Valid @RequestBody UpdateEventRequest req) {
        Event e = eventService.get(id);
        e.setVenueId(req.venueId());
        e.setName(req.name());
        e.setDescription(req.description());
        e.setStartAt(req.startAt());
        e.setEndAt(req.endAt());
        if (req.status() != null) e.setStatus(req.status());
        e.setCategory(req.category());
        e.setImageUrl(req.imageUrl());
        return ResponseEntity.ok(eventService.update(e));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> get(@PathVariable Long id) { return ResponseEntity.ok(eventService.get(id)); }

    @GetMapping
    public ResponseEntity<Page<Event>> list(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(eventService.list(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
