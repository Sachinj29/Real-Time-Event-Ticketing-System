package com.example.ticketing.venues.repo;

import com.example.ticketing.venues.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {}
