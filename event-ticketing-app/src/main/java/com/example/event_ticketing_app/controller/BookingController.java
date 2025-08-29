package com.example.event_ticketing_app.controller;

//import com.example.event_ticketing_app.entity.Booking;
//import com.example.event_ticketing_app.service.BookingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/bookings")
//@RequiredArgsConstructor
//public class BookingController {
//
//    private final BookingService bookingService;
//
//    @PostMapping
//    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest request) {
//        try {
//            Booking booking = bookingService.createBooking(
//                    request.getEventId(),
//                    request.getUserId(),
//                    request.getTicketsCount()
//            );
//            return ResponseEntity.ok(booking);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Booking> getBookingById(@PathVariable UUID id) {
//        return bookingService.getBookingById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/reference/{reference}")
//    public ResponseEntity<Booking> getBookingByReference(@PathVariable String reference) {
//        return bookingService.getBookingByReference(reference)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable UUID userId) {
//        return ResponseEntity.ok(bookingService.getUserBookings(userId));
//    }
//
//    @GetMapping("/event/{eventId}")
//    public ResponseEntity<List<Booking>> getEventBookings(@PathVariable UUID eventId) {
//        return ResponseEntity.ok(bookingService.getEventBookings(eventId));
//    }
//
//    @GetMapping("/event/{eventId}/available-seats")
//    public ResponseEntity<Map<String, Integer>> getAvailableSeats(@PathVariable UUID eventId) {
//        Integer availableSeats = bookingService.getAvailableSeats(eventId);
//        return ResponseEntity.ok(Map.of("availableSeats", availableSeats));
//    }
//
//    @PutMapping("/{id}/confirm")
//    public ResponseEntity<Booking> confirmBooking(@PathVariable UUID id) {
//        try {
//            Booking booking = bookingService.confirmBooking(id);
//            return ResponseEntity.ok(booking);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @PutMapping("/{id}/cancel")
//    public ResponseEntity<Booking> cancelBooking(@PathVariable UUID id) {
//        try {
//            Booking booking = bookingService.cancelBooking(id);
//            return ResponseEntity.ok(booking);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    // DTO for create booking request
//    public static class CreateBookingRequest {
//        private UUID eventId;
//        private UUID userId;
//        private Integer ticketsCount;
//
//        // Getters and setters
//        public UUID getEventId() { return eventId; }
//        public void setEventId(UUID eventId) { this.eventId = eventId; }
//
//        public UUID getUserId() { return userId; }
//        public void setUserId(UUID userId) { this.userId = userId; }
//
//        public Integer getTicketsCount() { return ticketsCount; }
//        public void setTicketsCount(Integer ticketsCount) { this.ticketsCount = ticketsCount; }
//    }
//}





import com.example.event_ticketing_app.entity.Booking;
import com.example.event_ticketing_app.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // DTO class for create booking request
    public static class CreateBookingRequest {
        private UUID eventId;
        private UUID userId;
        private Integer ticketsCount;

        // Getters and setters
        public UUID getEventId() { return eventId; }
        public void setEventId(UUID eventId) { this.eventId = eventId; }

        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }

        public Integer getTicketsCount() { return ticketsCount; }
        public void setTicketsCount(Integer ticketsCount) { this.ticketsCount = ticketsCount; }
    }

//    @PostMapping
//    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest request) {
//        try {
//            Booking booking = bookingService.createBooking(
//                    request.getEventId(),
//                    request.getUserId(),
//                    request.getTicketsCount()
//            );
//            return ResponseEntity.ok(booking);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
//        }
//    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest request) {
        try {
            // Create booking with PENDING status (no payment yet)
            Booking booking = bookingService.createBooking(
                    request.getEventId(),
                    request.getUserId(),
                    request.getTicketsCount()
            );

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "booking", booking,
                    "message", "Booking created. Please proceed to payment.",
                    "nextStep", "/api/payments/create-order/" + booking.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable UUID id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<?> getBookingByReference(@PathVariable String reference) {
        return bookingService.getBookingByReference(reference)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Booking>> getEventBookings(@PathVariable UUID eventId) {
        return ResponseEntity.ok(bookingService.getEventBookings(eventId));
    }

    @GetMapping("/event/{eventId}/available-seats")
    public ResponseEntity<Map<String, Integer>> getAvailableSeats(@PathVariable UUID eventId) {
        Integer availableSeats = bookingService.getAvailableSeats(eventId);
        return ResponseEntity.ok(Map.of("availableSeats", availableSeats));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmBooking(@PathVariable UUID id) {
        try {
            Booking booking = bookingService.confirmBooking(id);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable UUID id) {
        try {
            Booking booking = bookingService.cancelBooking(id);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}