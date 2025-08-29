package com.example.event_ticketing_app.service;

import com.example.event_ticketing_app.dto.BookingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendBookingConfirmation(BookingMessage booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("your.email@gmail.com"); // Replace with your email
            message.setTo(booking.getUserEmail());
            message.setSubject("🎫 Booking Confirmed - " + booking.getEventTitle());

            String emailBody = buildBookingConfirmationEmail(booking);
            message.setText(emailBody);

            mailSender.send(message);

            log.info("✅ Booking confirmation email sent successfully to: {}", booking.getUserEmail());

        } catch (Exception e) {
            log.error("❌ Failed to send booking confirmation email to: {}", booking.getUserEmail(), e);
        }
    }

    private String buildBookingConfirmationEmail(BookingMessage booking) {
        return String.format("""
            Dear %s,
            
            🎉 Your booking has been confirmed!
            
            📋 BOOKING DETAILS:
            ━━━━━━━━━━━━━━━━━━━━
            Event: %s
            Tickets: %d
            Total Amount: ₹%.2f
            Booking Reference: %s
            Booking Time: %s
            
            📍 VENUE INFORMATION:
            ━━━━━━━━━━━━━━━━━━━━
            Venue: %s
            Address: %s
            Event Date: %s
            
            ⚠️ IMPORTANT NOTES:
            ━━━━━━━━━━━━━━━━━━━━
            • Please arrive 30 minutes before the event starts
            • Bring a valid ID for entry verification
            • Keep this email as proof of purchase
            
            Thank you for choosing our event ticketing platform!
            
            Best regards,
            Event Ticketing Team
            """,
                booking.getUserName(),
                booking.getEventTitle(),
                booking.getTicketsCount(),
                booking.getTotalAmount(),
                booking.getBookingReference(),
                booking.getBookingTime(),
                booking.getVenueName(),
                booking.getVenueAddress(),
                booking.getEventStartTime()
        );
    }
}