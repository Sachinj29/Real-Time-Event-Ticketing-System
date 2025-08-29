package com.example.event_ticketing_app.service;

//import com.example.event_ticketing_app.entity.Booking;
//import com.example.event_ticketing_app.entity.BookingStatus;
//import com.example.event_ticketing_app.entity.Event;
//import com.example.event_ticketing_app.repository.BookingRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class BookingService {
//
//    private final BookingRepository bookingRepository;
//    private final EventService eventService;
//
//    @Transactional
//    public Booking createBooking(UUID eventId, UUID userId, Integer ticketsCount) {
//        // Validate event exists
//        Event event = eventService.getEventById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        // Check seat availability
//        Integer bookedSeats = bookingRepository.getTotalBookedSeatsForEvent(eventId);
//        if (bookedSeats == null) bookedSeats = 0;
//
//        Integer availableSeats = event.getTotalSeats() - bookedSeats;
//
//        if (availableSeats < ticketsCount) {
//            throw new RuntimeException("Not enough seats available. Available: " + availableSeats);
//        }
//
//        // Calculate total amount
//        Double totalAmount = event.getTicketPrice() * ticketsCount;
//
//        // Create booking
//        Booking booking = Booking.builder()
//                .eventId(eventId)
//                .userId(userId)
//                .ticketsCount(ticketsCount)
//                .totalAmount(totalAmount)
//                .status(BookingStatus.PENDING)
//                .build();
//
//        return bookingRepository.save(booking);
//    }
//
//    public Optional<Booking> getBookingById(UUID bookingId) {
//        return bookingRepository.findById(bookingId);
//    }
//
//    public Optional<Booking> getBookingByReference(String bookingReference) {
//        return bookingRepository.findByBookingReference(bookingReference);
//    }
//
//    public List<Booking> getUserBookings(UUID userId) {
//        return bookingRepository.findByUserId(userId);
//    }
//
//    public List<Booking> getEventBookings(UUID eventId) {
//        return bookingRepository.findByEventId(eventId);
//    }
//
//    @Transactional
//    public Booking confirmBooking(UUID bookingId) {
//        Booking booking = getBookingById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setStatus(BookingStatus.CONFIRMED);
//        return bookingRepository.save(booking);
//    }
//
//    @Transactional
//    public Booking cancelBooking(UUID bookingId) {
//        Booking booking = getBookingById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setStatus(BookingStatus.CANCELLED);
//        return bookingRepository.save(booking);
//    }
//
//    public Integer getAvailableSeats(UUID eventId) {
//        Event event = eventService.getEventById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        Integer bookedSeats = bookingRepository.getTotalBookedSeatsForEvent(eventId);
//        if (bookedSeats == null) bookedSeats = 0;
//
//        return event.getTotalSeats() - bookedSeats;
//    }
//}


//import com.example.event_ticketing_app.dto.BookingMessage;
//import com.example.event_ticketing_app.entity.Booking;
//import com.example.event_ticketing_app.entity.BookingStatus;
//import com.example.event_ticketing_app.entity.Event;
//import com.example.event_ticketing_app.entity.User;
//import com.example.event_ticketing_app.repository.BookingRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class BookingService {
//
//    private final BookingRepository bookingRepository;
//    private final EventService eventService;
//    private final UserService userService; // Now available
//    private final SeatLockingService seatLockingService;
//    private final MessagePublisherService messagePublisherService;
//
//    @Transactional
//    public Booking createBooking(UUID eventId, UUID userId, Integer ticketsCount) {
//        // First, try to acquire seat lock
//        if (!seatLockingService.lockSeats(eventId, ticketsCount, userId)) {
//            throw new RuntimeException("Unable to lock seats for booking. Please try again.");
//        }
//
//        try {
//            // Validate event exists
//            Event event = eventService.getEventById(eventId)
//                    .orElseThrow(() -> new RuntimeException("Event not found"));
//
//            // Check seat availability
//            Integer bookedSeats = bookingRepository.getTotalBookedSeatsForEvent(eventId);
//            if (bookedSeats == null) bookedSeats = 0;
//
//            Integer availableSeats = event.getTotalSeats() - bookedSeats;
//
//            if (availableSeats < ticketsCount) {
//                seatLockingService.releaseSeatLock(eventId, userId);
//                throw new RuntimeException("Not enough seats available. Available: " + availableSeats);
//            }
//
//            // Calculate total amount
//            Double totalAmount = event.getTicketPrice() * ticketsCount;
//
//            // Create booking
//            Booking booking = Booking.builder()
//                    .eventId(eventId)
//                    .userId(userId)
//                    .ticketsCount(ticketsCount)
//                    .totalAmount(totalAmount)
//                    .status(BookingStatus.PENDING)
//                    .build();
//
//            Booking savedBooking = bookingRepository.save(booking);
//            log.info("Booking created successfully: {}", savedBooking.getId());
//
//            return savedBooking;
//
//        } catch (Exception e) {
//            // Release lock on any error
//            seatLockingService.releaseSeatLock(eventId, userId);
//            throw e;
//        }
//    }
//
//    @Transactional
//    public Booking confirmBooking(UUID bookingId) {
//        Booking booking = getBookingById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setStatus(BookingStatus.CONFIRMED);
//        Booking confirmedBooking = bookingRepository.save(booking);
//
//        // Release the seat lock after confirmation
//        seatLockingService.releaseSeatLock(booking.getEventId(), booking.getUserId());
//
//        // Publish booking confirmation message to CloudAMQP
//        publishBookingMessage(confirmedBooking);
//
//        return confirmedBooking;
//    }
//
//    public List<Booking> getUserBookings(UUID userId) {
//        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId); // Now available
//    }
//
//    public Optional<Booking> getBookingById(UUID bookingId) {
//        return bookingRepository.findById(bookingId);
//    }
//
//    // Add these methods to your BookingService.java
//
//    public Optional<Booking> getBookingByReference(String reference) {
//        return bookingRepository.findByBookingReference(reference);
//    }
//
//    public List<Booking> getEventBookings(UUID eventId) {
//        return bookingRepository.findByEventId(eventId);
//    }
//
//    public Integer getAvailableSeats(UUID eventId) {
//        Event event = eventService.getEventById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        Integer bookedSeats = bookingRepository.getTotalBookedSeatsForEvent(eventId);
//        if (bookedSeats == null) bookedSeats = 0;
//
//        return event.getTotalSeats() - bookedSeats;
//    }
//
//    @Transactional
//    public Booking cancelBooking(UUID bookingId) {
//        Booking booking = getBookingById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setStatus(BookingStatus.CANCELLED);
//        Booking cancelledBooking = bookingRepository.save(booking);
//
//        // Release the seat lock
//        seatLockingService.releaseSeatLock(booking.getEventId(), booking.getUserId());
//
//        return cancelledBooking;
//    }
//
//
//    private void publishBookingMessage(Booking booking) {
//        try {
//            // Fetch event and user details
//            Event event = eventService.getEventById(booking.getEventId())
//                    .orElseThrow(() -> new RuntimeException("Event not found"));
//
//            User user = userService.getUserById(booking.getUserId()) // Now available
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            // Create booking message using firstName + lastName (not getName())
//            String fullName = user.getFirstName() + " " + user.getLastName();
//
//            BookingMessage bookingMessage = new BookingMessage(
//                    booking.getId(),
//                    booking.getUserId(),
//                    booking.getEventId(),
//                    event.getTitle(),
//                    user.getEmail(), // Available from your User entity
//                    fullName,        // Using firstName + lastName
//                    booking.getTicketsCount(),
//                    booking.getTotalAmount(),
//                    booking.getCreatedAt(),
//                    event.getVenueName(),
//                    event.getVenueAddress(),
//                    event.getStartDateTime(),
//                    booking.getStatus().toString(),
//                    "BOOKING_CREATED"
//            );
//
//            // Publish to CloudAMQP
//            messagePublisherService.publishBookingCreated(bookingMessage);
//
//        } catch (Exception e) {
//            log.error("Failed to publish booking message: {}", e.getMessage(), e);
//            // Don't fail the booking if message publishing fails
//        }
//    }
//}


import com.example.event_ticketing_app.entity.Booking;
import com.example.event_ticketing_app.entity.BookingStatus;
import com.example.event_ticketing_app.entity.Event;
import com.example.event_ticketing_app.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventService eventService;
    private final MessagePublisherService messagePublisherService;

    @Transactional
    @Retryable(value = {OptimisticLockingFailureException.class, ObjectOptimisticLockingFailureException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 100))
    public Booking createBooking(UUID eventId, UUID userId, Integer ticketsCount) {
        log.info("🎫 Creating booking for user {} on event {} for {} tickets", userId, eventId, ticketsCount);

        try {
            // 1. Fetch event (this will be optimistically locked during transaction)
            Event event = eventService.getEventById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));

            log.info("📅 Event found: {} with {} total seats", event.getTitle(), event.getTotalSeats());

            // 2. Calculate current booked seats for this event
            Integer currentBookedSeats = bookingRepository.getTotalBookedSeatsForEvent(eventId);
            if (currentBookedSeats == null) {
                currentBookedSeats = 0;
            }

            // 3. Check seat availability
            Integer availableSeats = event.getTotalSeats() - currentBookedSeats;

            log.info("💺 Available seats: {} (Total: {}, Booked: {})",
                    availableSeats, event.getTotalSeats(), currentBookedSeats);

            if (availableSeats < ticketsCount) {
                throw new RuntimeException(String.format(
                        "Not enough seats available. Requested: %d, Available: %d",
                        ticketsCount, availableSeats));
            }

            // 4. Calculate total amount
            Double totalAmount = event.getTicketPrice() * ticketsCount;

            // 5. Generate booking reference
            String bookingReference = generateBookingReference();

            // 6. Create booking
            Booking booking = Booking.builder()
                    .eventId(eventId)
                    .userId(userId)
                    .ticketsCount(ticketsCount)
                    .totalAmount(totalAmount)
                    .status(BookingStatus.PENDING)
                    .bookingReference(bookingReference)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // 7. Save booking (this will trigger optimistic lock check on event if needed)
            Booking savedBooking = bookingRepository.save(booking);

            log.info("✅ Booking created successfully: {} with reference {}",
                    savedBooking.getId(), bookingReference);

            return savedBooking;

        } catch (OptimisticLockingFailureException e) {
            log.warn("⚠️ Optimistic lock conflict during booking creation - will retry");
            throw e; // Let @Retryable handle this
        } catch (Exception e) {
            log.error("❌ Failed to create booking: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to create booking: " + e.getMessage());
        }
    }

    @Transactional
    public Booking confirmBooking(UUID bookingId) {
        log.info("✅ Confirming booking: {}", bookingId);

        Booking booking = getBookingById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in PENDING status");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setUpdatedAt(LocalDateTime.now());

        Booking confirmedBooking = bookingRepository.save(booking);

        // Publish confirmation message to CloudAMQP
        try {
            messagePublisherService.publishBookingCreated(confirmedBooking);
            log.info("📨 Published booking confirmation message for: {}", bookingId);
        } catch (Exception e) {
            log.error("📨 Failed to publish booking message: {}", e.getMessage());
            // Don't fail the booking if messaging fails
        }

        return confirmedBooking;
    }

    public List<Booking> getUserBookings(UUID userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Booking> getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public Optional<Booking> getBookingByReference(String reference) {
        return bookingRepository.findByBookingReference(reference);
    }

    public List<Booking> getEventBookings(UUID eventId) {
        return bookingRepository.findByEventId(eventId);
    }

    public Integer getAvailableSeats(UUID eventId) {
        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Integer bookedSeats = bookingRepository.getTotalBookedSeatsForEvent(eventId);
        if (bookedSeats == null) bookedSeats = 0;

        return event.getTotalSeats() - bookedSeats;
    }

    @Transactional
    public Booking cancelBooking(UUID bookingId) {
        Booking booking = getBookingById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setUpdatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    private String generateBookingReference() {
        return "BK" + System.currentTimeMillis();
    }
}