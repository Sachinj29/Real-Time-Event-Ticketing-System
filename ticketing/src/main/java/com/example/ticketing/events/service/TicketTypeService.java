package com.example.ticketing.events.service;

import com.example.ticketing.events.domain.TicketType;
import com.example.ticketing.events.repo.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTypeService {
    private final TicketTypeRepository repo;

    public TicketType create(TicketType t) { return repo.save(t); }
    public TicketType update(TicketType t) { return repo.save(t); }
    public void delete(Long id) { repo.deleteById(id); }
    public List<TicketType> forEvent(Long eventId) { return repo.findByEventId(eventId); }
}
