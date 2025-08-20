package com.example.ticketing.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "reservations", indexes = {
        @Index(name="idx_res_event", columnList = "event_id")
})
@Getter @Setter
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(name="event_id", nullable=false)
    private Long eventId;

    @Column(columnDefinition = "jsonb", nullable=false)
    private String itemsJson; // [{seatId}|{ticketTypeId,qty,priceCents}]

    @Column(nullable=false)
    private String status = "PENDING"; // PENDING, EXPIRED, CONFIRMED, CANCELED

    @Column(name="reserved_until", nullable=false)
    private Instant reservedUntil;

    @Column(name="idempotency_key", unique = true)
    private String idempotencyKey;

    @Column(name="created_at", nullable=false)
    private Instant createdAt = Instant.now();
}
