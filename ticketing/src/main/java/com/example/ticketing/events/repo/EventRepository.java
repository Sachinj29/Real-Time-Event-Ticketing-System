package com.example.ticketing.events.repo;

import com.example.ticketing.events.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {}
