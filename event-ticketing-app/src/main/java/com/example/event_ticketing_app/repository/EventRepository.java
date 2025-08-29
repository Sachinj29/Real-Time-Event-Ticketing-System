package com.example.event_ticketing_app.repository;

import com.example.event_ticketing_app.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByCity(String city);

    List<Event> findByState(String state);

    List<Event> findByCountry(String country);

    // Find events within a certain radius (in kilometers)
    @Query(value = "SELECT * FROM events WHERE " +
            "6371 * acos(cos(radians(:latitude)) * cos(radians(venue_latitude)) * " +
            "cos(radians(venue_longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(venue_latitude))) <= :radius",
            nativeQuery = true)
    List<Event> findEventsWithinRadius(@Param("latitude") Double latitude,
                                       @Param("longitude") Double longitude,
                                       @Param("radius") Double radius);
}