package com.example.ticketing.orders.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "tickets", indexes = {
        @Index(name="idx_ticket_code", columnList = "ticket_code", unique = true)
})
@Getter @Setter
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_id", nullable=false)
    private Long orderId;

    @Column(name="event_id", nullable=false)
    private Long eventId;

    @Column(name="ticket_code", nullable=false, unique=true)
    private String ticketCode;

    private String attendeeName;
    private String attendeeEmail;
    private Instant checkedInAt;
}
