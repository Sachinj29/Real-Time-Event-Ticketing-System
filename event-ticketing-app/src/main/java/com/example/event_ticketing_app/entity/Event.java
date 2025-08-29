////package com.eventtickets.event_ticketing_backend.entity;
////
////import jakarta.persistence.*;
////import lombok.*;
////import java.time.LocalDateTime;
////import java.util.UUID;
////
////@Entity
////@Table(name = "events")
////@Data
////@Builder
////@NoArgsConstructor
////@AllArgsConstructor
////public class Event {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.UUID)
////    private UUID id;
////
////    @Column(nullable = false)
////    private String title;
////
////    @Column(columnDefinition = "TEXT")
////    private String description;
////
////    @Column(nullable = false)
////    private LocalDateTime startDateTime;
////
////    @Column(nullable = false)
////    private LocalDateTime endDateTime;
////
////    @Column(nullable = false)
////    private Integer totalSeats;
////
////    @Column(nullable = false)
////    private Double ticketPrice;
////
////    @Column(nullable = false)
////    private UUID organizerId;
////
////    @Column(name = "created_at")
////    private LocalDateTime createdAt;
////
////    @Column(name = "updated_at")
////    private LocalDateTime updatedAt;
////
////    @PrePersist
////    protected void onCreate() {
////        createdAt = LocalDateTime.now();
////        updatedAt = LocalDateTime.now();
////    }
////
////    @PreUpdate
////    protected void onUpdate() {
////        updatedAt = LocalDateTime.now();
////    }
////}
//



//2:]

//package com.example.event_ticketing_app.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.*;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Table(name = "events")
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Event {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @NotBlank(message = "Title is required")
//    @Size(max = 100, message = "Title cannot exceed 100 characters")
//    private String title;
//
//    @NotBlank(message = "Description is required")
//    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
//    @Column(columnDefinition = "TEXT")
//    private String description;
//
//    @NotNull(message = "Start date/time is required")
//    private LocalDateTime startDateTime;
//
//    @NotNull(message = "End date/time is required")
//    private LocalDateTime endDateTime;
//
//    @NotNull(message = "Total seats is required")
//    @Positive(message = "Total seats must be a positive number")
//    private Integer totalSeats;
//
//    @NotNull(message = "Ticket price is required")
//    @DecimalMin(value = "0.0", inclusive = false, message = "Ticket price must be greater than zero")
//    private Double ticketPrice;
//
//    @NotNull(message = "Organizer ID is required")
//    private UUID organizerId;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
//}



//3]:


package com.example.event_ticketing_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Start date/time is required")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date/time is required")
    private LocalDateTime endDateTime;

    @NotNull(message = "Total seats is required")
    @Positive(message = "Total seats must be a positive number")
    private Integer totalSeats;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Ticket price must be greater than zero")
    private Double ticketPrice;

    @NotNull(message = "Organizer ID is required")
    private UUID organizerId;

    // New Venue Fields
//    @NotBlank(message = "Venue name is required")
//    @Size(max = 255, message = "Venue name cannot exceed 255 characters")
//    @Column(name = "venue_name")
//    private String venueName;
//
//    @NotBlank(message = "Venue address is required")
//    @Size(max = 500, message = "Venue address cannot exceed 500 characters")
//    @Column(name = "venue_address")
//    private String venueAddress;

    @Size(max = 255, message = "Venue name cannot exceed 255 characters")
    @Column(name = "venue_name")
    private String venueName;

    @Size(max = 500, message = "Venue address cannot exceed 500 characters")
    @Column(name = "venue_address")
    private String venueAddress;

    @Column(name = "venue_latitude")
    private Double venueLatitude;

    @Column(name = "venue_longitude")
    private Double venueLongitude;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}






