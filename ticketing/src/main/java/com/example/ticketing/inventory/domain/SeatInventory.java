package com.example.ticketing.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "seat_inventory",
        uniqueConstraints = @UniqueConstraint(name="uk_event_seat", columnNames = {"event_id","seat_id"}))
@Getter @Setter
public class SeatInventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="event_id", nullable=false)
    private Long eventId;

    @Column(name="seat_id", nullable=false)
    private Long seatId;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private InventoryStatus status = InventoryStatus.AVAILABLE;

    @Column(name="lock_expires_at")
    private Instant lockExpiresAt;

    @Version
    private Long version;
}
