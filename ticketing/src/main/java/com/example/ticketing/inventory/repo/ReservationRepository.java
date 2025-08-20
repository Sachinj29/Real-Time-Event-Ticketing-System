package com.example.ticketing.inventory.repo;

import com.example.ticketing.inventory.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByIdempotencyKey(String key);
}
