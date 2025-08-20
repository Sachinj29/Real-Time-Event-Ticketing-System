package com.example.ticketing.events.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "ticket_types")
@Getter @Setter
public class TicketType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long eventId;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private Long priceCents;

    @Column(nullable=false)
    private String currency;

    @Column(nullable=false)
    private Integer quota;

    private Instant salesStart;
    private Instant salesEnd;

    private Integer minPerOrder = 1;
    private Integer maxPerOrder = 10;
}
