package com.example.ticketing.outbox.service;

import com.example.ticketing.outbox.domain.OutboxEvent;
import com.example.ticketing.outbox.repo.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxRepository outbox;

    @Transactional
    public void add(String aggregateType, String aggregateId, String eventType, String payload) {
        OutboxEvent e = new OutboxEvent();
        e.setAggregateType(aggregateType);
        e.setAggregateId(aggregateId);
        e.setEventType(eventType);
        e.setPayload(payload);
        outbox.save(e);
    }
}
