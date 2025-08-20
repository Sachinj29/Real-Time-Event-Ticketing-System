package com.example.ticketing.identity.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String roles; // CSV or single role e.g., "USER"

    @Column(nullable = false)
    private String status; // ACTIVE, SUSPENDED, DELETED

    private String name;
    private Instant createdAt = Instant.now();
}
