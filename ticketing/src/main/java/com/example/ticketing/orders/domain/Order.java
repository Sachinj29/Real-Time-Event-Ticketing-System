package com.example.ticketing.orders.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long reservationId;

    @Column(nullable=false)
    private Long userId;

    @Column(nullable=false)
    private Long amountCents;

    @Column(nullable=false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private OrderStatus status = OrderStatus.REQUIRES_PAYMENT;

    private String provider; // razorpay
    private String providerPaymentId;

    @Column(nullable=false)
    private Instant createdAt = Instant.now();
}
