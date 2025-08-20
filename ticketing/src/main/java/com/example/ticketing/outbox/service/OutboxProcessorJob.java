package com.example.ticketing.outbox.service;

import com.example.ticketing.outbox.domain.OutboxEvent;
import com.example.ticketing.outbox.repo.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxProcessorJob {
    private static final Logger log = LoggerFactory.getLogger(OutboxProcessorJob.class);

    private final OutboxRepository outbox;

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void process() {
        List<OutboxEvent> batch = outbox.findTop100ByProcessedAtIsNullOrderByIdAsc();
        for (OutboxEvent e : batch) {
            // TODO: publish to RabbitMQ or SSE as needed
            e.setProcessedAt(Instant.now());
        }
    }
}
