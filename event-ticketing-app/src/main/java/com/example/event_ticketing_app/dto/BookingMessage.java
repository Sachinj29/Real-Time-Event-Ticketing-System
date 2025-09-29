package com.example.event_ticketing_app.dto;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
////public class BookingMessage {
////    private UUID bookingId;
////    private UUID userId;
////    private UUID eventId;
////    private String eventTitle;
////    private String userEmail;
////    private String userName;
////    private Integer ticketsCount;
////    private Double totalAmount;
////
////    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
////    private LocalDateTime bookingTime;
////
////    private String venueName;
////    private String venueAddress;
////
////    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
////    private LocalDateTime eventStartTime;
////
////    private String bookingStatus;
////    private String messageType;
////}
//
//
//public class BookingMessage {
//    private UUID bookingId;
//    private UUID eventId;
//    private UUID userId;
//    private String userEmail;
//    private String userName;
//    private String eventTitle;
//    private String eventStartTime;
//    private String venueName;
//    private String venueAddress;
//    private Integer ticketsCount;
//    private Double totalAmount;
//    private String bookingReference;
//    private String status;
//    private LocalDateTime bookingTime;
//    private String messageType;
//
//    // Static factory method instead of builder
//    public static BookingMessage create(UUID bookingId, UUID eventId, UUID userId,
//                                        String userEmail, String userName, String eventTitle,
//                                        String eventStartTime, String venueName, String venueAddress,
//                                        Integer ticketsCount, Double totalAmount, String bookingReference,
//                                        String status, LocalDateTime bookingTime) {
//        BookingMessage message = new BookingMessage();
//        message.setBookingId(bookingId);
//        message.setEventId(eventId);
//        message.setUserId(userId);
//        message.setUserEmail(userEmail);
//        message.setUserName(userName);
//        message.setEventTitle(eventTitle);
//        message.setEventStartTime(eventStartTime);
//        message.setVenueName(venueName);
//        message.setVenueAddress(venueAddress);
//        message.setTicketsCount(ticketsCount);
//        message.setTotalAmount(totalAmount);
//        message.setBookingReference(bookingReference);
//        message.setStatus(status);
//        message.setBookingTime(bookingTime);
//        return message;
//    }
//}


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingMessage {
    private UUID bookingId;
    private UUID eventId;
    private UUID userId;
    private String userEmail;
    private String userName;
    private String eventTitle;
    private String eventStartTime;
    private String venueName;
    private String venueAddress;
    private Integer ticketsCount;
    private Double totalAmount;
    private String bookingReference;
    private String status;
    private LocalDateTime bookingTime;
    private String messageType;

    // Static factory method instead of builder
    public static BookingMessage create(UUID bookingId, UUID eventId, UUID userId,
                                        String userEmail, String userName, String eventTitle,
                                        String eventStartTime, String venueName, String venueAddress,
                                        Integer ticketsCount, Double totalAmount, String bookingReference,
                                        String status, LocalDateTime bookingTime) {
        BookingMessage message = new BookingMessage();
        message.setBookingId(bookingId);
        message.setEventId(eventId);
        message.setUserId(userId);
        message.setUserEmail(userEmail);
        message.setUserName(userName);
        message.setEventTitle(eventTitle);
        message.setEventStartTime(eventStartTime);
        message.setVenueName(venueName);
        message.setVenueAddress(venueAddress);
        message.setTicketsCount(ticketsCount);
        message.setTotalAmount(totalAmount);
        message.setBookingReference(bookingReference);
        message.setStatus(status);
        message.setBookingTime(bookingTime);
        return message;
    }
}
