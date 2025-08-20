package com.example.ticketing.events.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "events")
@Getter @Setter
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long organizerId;

    @Column(nullable=false)
    private Long venueId;

    @Column(nullable=false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable=false)
    private Instant startAt;

    @Column(nullable=false)
    private Instant endAt;

    @Column(nullable=false)
    private String status = "DRAFT"; // DRAFT, PUBLISHED, CANCELED, ENDED

    private String category;
    private String imageUrl;
    private Instant createdAt = Instant.now();
}
