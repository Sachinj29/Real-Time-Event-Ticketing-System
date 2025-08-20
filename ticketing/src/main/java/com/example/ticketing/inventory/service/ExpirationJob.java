package com.example.ticketing.inventory.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpirationJob {
    private static final Logger log = LoggerFactory.getLogger(ExpirationJob.class);
    private final InventoryService inventory;

    @Scheduled(fixedDelay = 10000)
    public void releaseExpired() {
        int released = inventory.releaseAllExpired();
        if (released > 0) {
            log.info("Released {} expired locks", released);
        }
    }
}
