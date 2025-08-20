package com.example.ticketing.venues.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "venues")
@Getter @Setter
public class Venue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    private String address;

    @Column(nullable=false)
    private String timezone;

    @Column(columnDefinition = "jsonb")
    private String seatMapJson;

    private Instant createdAt = Instant.now();
}
