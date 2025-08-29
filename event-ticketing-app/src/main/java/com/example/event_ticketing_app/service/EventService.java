//package com.example.event_ticketing_app.service;
//
//import com.example.event_ticketing_app.entity.Event;
//import com.example.event_ticketing_app.repository.EventRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class EventService {
//
//    private final EventRepository eventRepository;
//
//    public Event createEvent(Event event) {
//        return eventRepository.save(event);
//    }
//
//    public Optional<Event> getEventById(UUID eventId) {
//        return eventRepository.findById(eventId);
//    }
//
//    public List<Event> getAllEvents() {
//        return eventRepository.findAll();
//    }
//
//    public Event updateEvent(Event event) {
//        return eventRepository.save(event);
//    }
//
//    public void deleteEvent(UUID eventId) {
//        eventRepository.deleteById(eventId);
//    }
//}


//package com.example.event_ticketing_app.service;

//import com.example.event_ticketing_app.entity.Event;
//import com.example.event_ticketing_app.repository.EventRepository;
//import com.example.event_ticketing_app.service.GeocodingService.AddressDetails;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class EventService {
//
//    private final EventRepository eventRepository;
//    private final GeocodingService geocodingService;
//
//    public Event createEvent(Event event) {
//        // Geocode the venue address if coordinates are not provided
//        if (event.getVenueLatitude() == null || event.getVenueLongitude() == null) {
//            String fullAddress = event.getVenueAddress();
//            AddressDetails details = geocodingService.getAddressDetails(fullAddress);
//
//            if (details != null) {
//                event.setVenueLatitude(details.getLatitude());
//                event.setVenueLongitude(details.getLongitude());
//                event.setCity(details.getCity());
//                event.setState(details.getState());
//                event.setCountry(details.getCountry());
//                event.setPostalCode(details.getPostalCode());
//
//                // Update formatted address
//                if (details.getFormattedAddress() != null) {
//                    event.setVenueAddress(details.getFormattedAddress());
//                }
//            }
//        }
//
//        return eventRepository.save(event);
//    }
//
//    public Optional<Event> getEventById(UUID eventId) {
//        return eventRepository.findById(eventId);
//    }
//
//    public List<Event> getAllEvents() {
//        return eventRepository.findAll();
//    }
//
//    public Event updateEvent(Event event) {
//        // Re-geocode if address changed
//        if (event.getVenueLatitude() == null || event.getVenueLongitude() == null) {
//            String fullAddress = event.getVenueAddress();
//            AddressDetails details = geocodingService.getAddressDetails(fullAddress);
//
//            if (details != null) {
//                event.setVenueLatitude(details.getLatitude());
//                event.setVenueLongitude(details.getLongitude());
//                event.setCity(details.getCity());
//                event.setState(details.getState());
//                event.setCountry(details.getCountry());
//                event.setPostalCode(details.getPostalCode());
//            }
//        }
//
//        return eventRepository.save(event);
//    }
//
//    public void deleteEvent(UUID eventId) {
//        eventRepository.deleteById(eventId);
//    }
//
//    public List<Event> getEventsByCity(String city) {
//        return eventRepository.findByCity(city);
//    }
//}


package com.example.event_ticketing_app.service;


import com.example.event_ticketing_app.entity.Event;
import com.example.event_ticketing_app.repository.EventRepository;
import com.example.event_ticketing_app.service.GeocodingService.AddressDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final GeocodingService geocodingService;

    @CachePut(value = "events", key = "#result.id")
    public Event createEvent(Event event) {
        log.info("Creating event: {}", event.getTitle());

        // Geocode the venue address if coordinates are not provided
        if (event.getVenueLatitude() == null || event.getVenueLongitude() == null) {
            String fullAddress = event.getVenueAddress();
            log.info("Attempting to geocode address: {}", fullAddress);

            try {
                AddressDetails details = geocodingService.getAddressDetails(fullAddress);

                if (details != null) {
                    log.info("Geocoding successful: lat={}, lng={}", details.getLatitude(), details.getLongitude());
                    event.setVenueLatitude(details.getLatitude());
                    event.setVenueLongitude(details.getLongitude());
                    event.setCity(details.getCity());
                    event.setState(details.getState());
                    event.setCountry(details.getCountry());
                    event.setPostalCode(details.getPostalCode());

                    // Update formatted address
                    if (details.getFormattedAddress() != null) {
                        event.setVenueAddress(details.getFormattedAddress());
                    }
                } else {
                    log.warn("Geocoding returned null results for address: {}", fullAddress);
                }
            } catch (Exception e) {
                log.error("Geocoding failed for address: {}", fullAddress, e);
            }
        }

        return eventRepository.save(event);
    }

    @Cacheable(value = "events", key = "#eventId")
    public Optional<Event> getEventById(UUID eventId) {
        log.info("Fetching event from database: {}", eventId);
        return eventRepository.findById(eventId);
    }

    @Cacheable(value = "events", key = "'all'")
    public List<Event> getAllEvents() {
        log.info("Fetching all events from database");
        return eventRepository.findAll();
    }

    @CachePut(value = "events", key = "#event.id")
    @CacheEvict(value = "events", key = "'all'")
    public Event updateEvent(Event event) {
        log.info("Updating event: {}", event.getId());

        // Re-geocode if address changed
        if (event.getVenueLatitude() == null || event.getVenueLongitude() == null) {
            String fullAddress = event.getVenueAddress();
            AddressDetails details = geocodingService.getAddressDetails(fullAddress);

            if (details != null) {
                event.setVenueLatitude(details.getLatitude());
                event.setVenueLongitude(details.getLongitude());
                event.setCity(details.getCity());
                event.setState(details.getState());
                event.setCountry(details.getCountry());
                event.setPostalCode(details.getPostalCode());
            }
        }

        return eventRepository.save(event);
    }

    @CacheEvict(value = "events", allEntries = true)
    public void deleteEvent(UUID eventId) {
        log.info("Deleting event: {}", eventId);
        eventRepository.deleteById(eventId);
    }

    @Cacheable(value = "events", key = "'city:' + #city")
    public List<Event> getEventsByCity(String city) {
        log.info("Fetching events by city from database: {}", city);
        return eventRepository.findByCity(city);
    }
}