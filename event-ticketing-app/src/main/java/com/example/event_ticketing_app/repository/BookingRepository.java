package com.example.event_ticketing_app.repository;
//
//import com.example.event_ticketing_app.entity.Booking;
//import com.example.event_ticketing_app.entity.BookingStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public interface BookingRepository extends JpaRepository<Booking, UUID> {
//
//    List<Booking> findByUserId(UUID userId);
//
//    List<Booking> findByEventId(UUID eventId);
//
//    Optional<Booking> findByBookingReference(String bookingReference);
//
//    List<Booking> findByStatus(BookingStatus status);
//
//    @Query("SELECT SUM(b.ticketsCount) FROM Booking b WHERE b.eventId = :eventId AND b.status = 'CONFIRMED'")
//    Integer getTotalBookedSeatsForEvent(UUID eventId);
//}

import com.example.event_ticketing_app.entity.Booking;
import com.example.event_ticketing_app.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> { // Fixed generic types

    List<Booking> findByUserId(UUID userId);

    // Add this method for getUserBookings
    List<Booking> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<Booking> findByEventId(UUID eventId);

    Optional<Booking> findByBookingReference(String bookingReference);

    List<Booking> findByStatus(BookingStatus status);

    @Query("SELECT COALESCE(SUM(b.ticketsCount), 0) FROM Booking b WHERE b.eventId = :eventId AND b.status = 'CONFIRMED'")
    Integer getTotalBookedSeatsForEvent(@Param("eventId") UUID eventId);
}