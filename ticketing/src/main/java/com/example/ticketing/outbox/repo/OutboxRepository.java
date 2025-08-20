package com.example.ticketing.outbox.repo;

import com.example.ticketing.outbox.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findTop100ByProcessedAtIsNullOrderByIdAsc();
}
