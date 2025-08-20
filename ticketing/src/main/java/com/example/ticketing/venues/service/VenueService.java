package com.example.ticketing.venues.service;

import com.example.ticketing.venues.domain.Venue;
import com.example.ticketing.venues.repo.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venues;

    public Venue create(Venue v) { return venues.save(v); }
    public Venue update(Venue v) { return venues.save(v); }
    public void delete(Long id) { venues.deleteById(id); }
    public Venue get(Long id) { return venues.findById(id).orElseThrow(); }
    public Page<Venue> list(int page, int size) {
        return venues.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }
}
