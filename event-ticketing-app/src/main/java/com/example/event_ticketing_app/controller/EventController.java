package com.example.event_ticketing_app.controller;

import com.example.event_ticketing_app.entity.Event;
import com.example.event_ticketing_app.repository.EventRepository;
import com.example.event_ticketing_app.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid  @RequestBody Event event) {
        Event created = eventService.createEvent(event);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id, @RequestBody Event event) {
        if (!eventService.getEventById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        event.setId(id);
        Event updated = eventService.updateEvent(event);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        if (!eventService.getEventById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Event>> getEventsByCity(@PathVariable String city) {
        return ResponseEntity.ok(eventService.getEventsByCity(city));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Event>> getNearbyEvents(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "50.0") Double radius) {
        List<Event> nearbyEvents = eventRepository.findEventsWithinRadius(latitude, longitude, radius);
        return ResponseEntity.ok(nearbyEvents);
    }
}
