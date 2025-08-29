package com.example.event_ticketing_app.service;

//import com.example.event_ticketing_app.config.RabbitMQConfig;
//import com.example.event_ticketing_app.dto.BookingMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MessagePublisherService {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    public void publishBookingCreated(BookingMessage bookingMessage) {
//        try {
//            bookingMessage.setMessageType("BOOKING_CREATED");
//            log.info("Publishing booking created message for booking: {}", bookingMessage.getBookingId());
//
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.EVENT_EXCHANGE,
//                    RabbitMQConfig.BOOKING_ROUTING_KEY,
//                    bookingMessage
//            );
//
//            log.info("Successfully published booking message to CloudAMQP");
//        } catch (Exception e) {
//            log.error("Failed to publish booking message: {}", e.getMessage(), e);
//        }
//    }
//
//    public void publishEmailNotification(BookingMessage bookingMessage) {
//        try {
//            bookingMessage.setMessageType("EMAIL_NOTIFICATION");
//            log.info("Publishing email notification for booking: {}", bookingMessage.getBookingId());
//
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.EVENT_EXCHANGE,
//                    RabbitMQConfig.EMAIL_ROUTING_KEY,
//                    bookingMessage
//            );
//
//            log.info("Successfully published email notification to CloudAMQP");
//        } catch (Exception e) {
//            log.error("Failed to publish email notification: {}", e.getMessage(), e);
//        }
//    }
//
//    public void publishNotification(String notificationType, BookingMessage bookingMessage) {
//        try {
//            bookingMessage.setMessageType(notificationType);
//            String routingKey = "event.notification." + notificationType.toLowerCase();
//
//            log.info("Publishing {} notification for booking: {}", notificationType, bookingMessage.getBookingId());
//
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.EVENT_EXCHANGE,
//                    routingKey,
//                    bookingMessage
//            );
//
//            log.info("Successfully published {} notification to CloudAMQP", notificationType);
//        } catch (Exception e) {
//            log.error("Failed to publish notification: {}", e.getMessage(), e);
//        }
//    }
//}

import com.example.event_ticketing_app.config.RabbitMQConfig;
import com.example.event_ticketing_app.dto.BookingMessage;
import com.example.event_ticketing_app.entity.Booking;
import com.example.event_ticketing_app.entity.Event;
import com.example.event_ticketing_app.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//public class MessagePublisherService {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final EventService eventService;
//    private final UserService userService;
//
//    public void publishBookingCreated(Booking booking) {
//        try {
//            // Convert Booking entity to BookingMessage DTO
//            BookingMessage message = convertToBookingMessage(booking);
//            message.setMessageType("BOOKING_CREATED");
//
//            log.info("📨 Publishing booking created message for booking: {}", booking.getId());
//
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.EVENT_EXCHANGE,
//                    RabbitMQConfig.BOOKING_ROUTING_KEY,
//                    message
//            );
//
//            log.info("✅ Successfully published booking message to CloudAMQP");
//
//        } catch (Exception e) {
//            log.error("❌ Failed to publish booking message: {}", e.getMessage(), e);
//        }
//    }
//
//    public void publishEmailNotification(BookingMessage bookingMessage) {
//        try {
//            bookingMessage.setMessageType("EMAIL_NOTIFICATION");
//
//            log.info("📧 Publishing email notification for booking: {}", bookingMessage.getBookingId());
//
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.EVENT_EXCHANGE,
//                    RabbitMQConfig.EMAIL_ROUTING_KEY,
//                    bookingMessage
//            );
//
//            log.info("✅ Successfully published email notification to CloudAMQP");
//
//        } catch (Exception e) {
//            log.error("❌ Failed to publish email notification: {}", e.getMessage(), e);
//        }
//    }
//
//    public void publishNotification(String notificationType, BookingMessage bookingMessage) {
//        try {
//            bookingMessage.setMessageType(notificationType);
//            String routingKey = "event.notification." + notificationType.toLowerCase();
//
//            log.info("🔔 Publishing {} notification for booking: {}", notificationType, bookingMessage.getBookingId());
//
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.EVENT_EXCHANGE,
//                    routingKey,
//                    bookingMessage
//            );
//
//            log.info("✅ Successfully published {} notification to CloudAMQP", notificationType);
//
//        } catch (Exception e) {
//            log.error("❌ Failed to publish notification: {}", e.getMessage(), e);
//        }
//    }
//
//    private BookingMessage convertToBookingMessage(Booking booking) {
//        // Fetch additional data for the message
//        Event event = eventService.getEventById(booking.getEventId()).orElse(null);
//        User user = userService.getUserById(booking.getUserId()).orElse(null);
//
//        return BookingMessage.create(
//                booking.getId(),
//                booking.getEventId(),
//                booking.getUserId(),
//                user != null ? user.getEmail() : "",
//                user != null ? user.getFirstName() + " " + user.getLastName() : "",
//                event != null ? event.getTitle() : "",
//                event != null ? event.getStartDateTime().toString() : "",
//                event != null ? event.getVenueName() : "",
//                event != null ? event.getVenueAddress() : "",
//                booking.getTicketsCount(),
//                booking.getTotalAmount(),
//                booking.getBookingReference(),
//                booking.getStatus().toString(),
//                booking.getCreatedAt()
//        );
//    }
//}


public class MessagePublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final EventService eventService;
    private final UserService userService;

    public void publishBookingCreated(Booking booking) {
        try {
            // Convert Booking entity to BookingMessage DTO
            BookingMessage message = convertToBookingMessage(booking);
            message.setMessageType("BOOKING_CONFIRMED");

            log.info("📨 Publishing booking confirmed message for booking: {}", booking.getId());

            // Publish to booking queue
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EVENT_EXCHANGE,
                    RabbitMQConfig.BOOKING_ROUTING_KEY,
                    message
            );

            // Also publish to email queue for email notification
            publishEmailNotification(message);

            log.info("✅ Successfully published booking and email messages to CloudAMQP");

        } catch (Exception e) {
            log.error("❌ Failed to publish booking message: {}", e.getMessage(), e);
        }
    }


    public void publishEmailNotification(BookingMessage bookingMessage) {
        try {
            bookingMessage.setMessageType("EMAIL_NOTIFICATION");

            log.info("📧 Publishing email notification for booking: {}", bookingMessage.getBookingId());

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EVENT_EXCHANGE,
                    RabbitMQConfig.EMAIL_ROUTING_KEY,
                    bookingMessage
            );

            log.info("✅ Successfully published email notification to CloudAMQP");

        } catch (Exception e) {
            log.error("❌ Failed to publish email notification: {}", e.getMessage(), e);
        }
    }

    public void publishNotification(String notificationType, BookingMessage bookingMessage) {
        try {
            bookingMessage.setMessageType(notificationType);
            String routingKey = "event.notification." + notificationType.toLowerCase();

            log.info("🔔 Publishing {} notification for booking: {}", notificationType, bookingMessage.getBookingId());

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EVENT_EXCHANGE,
                    routingKey,
                    bookingMessage
            );

            log.info("✅ Successfully published {} notification to CloudAMQP", notificationType);

        } catch (Exception e) {
            log.error("❌ Failed to publish notification: {}", e.getMessage(), e);
        }
    }

    private BookingMessage convertToBookingMessage(Booking booking) {
        // Fetch additional data for the message
        Event event = eventService.getEventById(booking.getEventId()).orElse(null);
        User user = userService.getUserById(booking.getUserId()).orElse(null);

        return BookingMessage.create(
                booking.getId(),
                booking.getEventId(),
                booking.getUserId(),
                user != null ? user.getEmail() : "",
                user != null ? user.getFirstName() + " " + user.getLastName() : "",
                event != null ? event.getTitle() : "",
                event != null ? event.getStartDateTime().toString() : "",
                event != null ? event.getVenueName() : "",
                event != null ? event.getVenueAddress() : "",
                booking.getTicketsCount(),
                booking.getTotalAmount(),
                booking.getBookingReference(),
                booking.getStatus().toString(),
                booking.getCreatedAt()
        );
    }
}