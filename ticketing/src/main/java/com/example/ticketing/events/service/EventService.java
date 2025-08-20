package com.example.ticketing.events.service;

import com.example.ticketing.events.domain.Event;
import com.example.ticketing.events.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository events;

    public Event create(Event e) { return events.save(e); }
    public Event update(Event e) { return events.save(e); }
    public void delete(Long id) { events.deleteById(id); }
    public Event get(Long id) { return events.findById(id).orElseThrow(); }
    public Page<Event> list(int page, int size) {
        return events.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }
}
