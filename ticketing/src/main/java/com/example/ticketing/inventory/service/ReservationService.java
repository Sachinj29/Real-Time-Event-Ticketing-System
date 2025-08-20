package com.example.ticketing.inventory.service;

import com.example.ticketing.inventory.domain.Reservation;
import com.example.ticketing.inventory.repo.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservations;

    @Transactional
    public Reservation create(Long userId, Long eventId, String itemsJson, int ttlSeconds, String idemKey) {
        Optional<Reservation> existing = idemKey == null ? Optional.empty() : reservations.findByIdempotencyKey(idemKey);
        if (existing.isPresent()) return existing.get();

        Reservation r = new Reservation();
        r.setUserId(userId);
        r.setEventId(eventId);
        r.setItemsJson(itemsJson);
        r.setStatus("PENDING");
        r.setReservedUntil(Instant.now().plusSeconds(ttlSeconds));
        r.setIdempotencyKey(idemKey);
        return reservations.save(r);
    }

    @Transactional
    public void markExpired(Reservation r) {
        r.setStatus("EXPIRED");
        reservations.save(r);
    }

    @Transactional
    public void confirm(Reservation r) {
        r.setStatus("CONFIRMED");
        reservations.save(r);
    }

    public Optional<Reservation> findById(Long id) { return reservations.findById(id); }
}
