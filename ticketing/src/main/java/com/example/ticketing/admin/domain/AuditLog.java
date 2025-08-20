package com.example.ticketing.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "audit_logs")
@Getter @Setter
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long actorId;

    @Column(nullable=false)
    private String action;

    @Column(nullable=false)
    private String entity;

    @Column(nullable=false)
    private String entityId;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    private Instant createdAt = Instant.now();
}
