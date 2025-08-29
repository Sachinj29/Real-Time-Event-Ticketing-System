package com.example.event_ticketing_app.service;

import com.example.event_ticketing_app.config.RabbitMQConfig;
import com.example.event_ticketing_app.dto.BookingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumerService {

    private final MessagePublisherService messagePublisherService;

    @RabbitListener(queues = RabbitMQConfig.BOOKING_QUEUE)
    public void handleBookingCreated(BookingMessage bookingMessage) {
        try {
            log.info("📨 Received booking created message from CloudAMQP: {}", bookingMessage.getBookingId());

            // Process booking confirmation logic
            processBookingConfirmation(bookingMessage);

            // Trigger email notification
            messagePublisherService.publishEmailNotification(bookingMessage);

            // Send push notification
            messagePublisherService.publishNotification("BOOKING_CONFIRMED", bookingMessage);

            log.info("✅ Successfully processed booking message: {}", bookingMessage.getBookingId());

        } catch (Exception e) {
            log.error("❌ Failed to process booking message: {}", e.getMessage(), e);
            // TODO: Implement retry logic or dead letter queue
        }
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleEmailNotification(BookingMessage bookingMessage) {
        try {
            log.info("📧 Received email notification message from CloudAMQP: {}", bookingMessage.getBookingId());

            // TODO: Implement actual email sending with Gmail SMTP or SendGrid
            sendBookingConfirmationEmail(bookingMessage);

            log.info("✅ Successfully sent email for booking: {}", bookingMessage.getBookingId());

        } catch (Exception e) {
            log.error("❌ Failed to send email notification: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleNotification(BookingMessage bookingMessage) {
        try {
            log.info("🔔 Received {} notification from CloudAMQP: {}",
                    bookingMessage.getMessageType(), bookingMessage.getBookingId());

            // Process different types of notifications
            processNotification(bookingMessage);

            log.info("✅ Successfully processed notification: {}", bookingMessage.getMessageType());

        } catch (Exception e) {
            log.error("❌ Failed to process notification: {}", e.getMessage(), e);
        }
    }

    private void processBookingConfirmation(BookingMessage bookingMessage) {
        // Business logic for booking confirmation
        log.info("🎫 Processing booking confirmation for user: {} | Event: {}",
                bookingMessage.getUserEmail(), bookingMessage.getEventTitle());

        // - Generate QR codes or tickets
        // - Update analytics
        // - Update seat availability cache
        // - Log audit trail
    }

    private void sendBookingConfirmationEmail(BookingMessage bookingMessage) {
        // Email sending logic
        log.info("📧 Sending booking confirmation email to: {}", bookingMessage.getUserEmail());
        log.info("📅 Event: {} | 🎫 Tickets: {} | 💰 Total: ${}",
                bookingMessage.getEventTitle(),
                bookingMessage.getTicketsCount(),
                bookingMessage.getTotalAmount());
        log.info("📍 Venue: {} | ⏰ Date: {}",
                bookingMessage.getVenueName(),
                bookingMessage.getEventStartTime());

        // TODO: Replace with actual email service
        // emailService.sendBookingConfirmation(bookingMessage);
    }

    private void processNotification(BookingMessage bookingMessage) {
        String messageType = bookingMessage.getMessageType();

        switch (messageType) {
            case "BOOKING_CONFIRMED":
                log.info("🎉 Sending booking confirmation push notification");
                break;
            case "EVENT_REMINDER":
                log.info("⏰ Sending event reminder notification");
                break;
            case "EVENT_CANCELLED":
                log.info("❌ Sending event cancellation notification");
                break;
            default:
                log.info("📱 Processing generic notification: {}", messageType);
        }

        // TODO: Implement actual push notification service
        // pushNotificationService.send(bookingMessage);
    }
}