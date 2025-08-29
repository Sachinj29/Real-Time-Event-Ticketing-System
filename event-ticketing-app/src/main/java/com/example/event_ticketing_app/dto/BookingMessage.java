package com.example.event_ticketing_app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingMessage {
    private UUID bookingId;
    private UUID userId;
    private UUID eventId;
    private String eventTitle;
    private String userEmail;
    private String userName;
    private Integer ticketsCount;
    private Double totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingTime;

    private String venueName;
    private String venueAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventStartTime;

    private String bookingStatus;
    private String messageType;
}