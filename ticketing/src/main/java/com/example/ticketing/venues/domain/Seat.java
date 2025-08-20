package com.example.ticketing.venues.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Getter @Setter
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long venueId;

    private String sectionLabel;
    private String rowLabel;
    private String seatNumber;
    private String type; // REGULAR/VIP/ACCESSIBLE
    private String meta; // JSON if needed
}
