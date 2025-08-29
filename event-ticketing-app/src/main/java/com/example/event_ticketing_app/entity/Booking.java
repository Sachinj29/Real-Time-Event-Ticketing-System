package com.example.event_ticketing_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Event ID is required")
    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotNull(message = "Number of tickets is required")
    @Positive(message = "Number of tickets must be positive")
    @Column(name = "tickets_count", nullable = false)
    private Integer ticketsCount;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be positive")
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "booking_reference", unique = true)
    private String bookingReference;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        // Generate booking reference
        if (bookingReference == null) {
            bookingReference = "BK" + System.currentTimeMillis();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


