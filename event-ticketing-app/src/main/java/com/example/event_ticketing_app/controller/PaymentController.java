package com.example.event_ticketing_app.controller;

import com.example.event_ticketing_app.dto.PaymentOrderResponse;
import com.example.event_ticketing_app.entity.Booking;
import com.example.event_ticketing_app.service.BookingService;
import com.example.event_ticketing_app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingService bookingService;

    @PostMapping("/create-order/{bookingId}")
    public ResponseEntity<?> createPaymentOrder(@PathVariable UUID bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            if (!"PENDING".equals(booking.getStatus().toString())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Booking is not in pending state"));
            }

            PaymentOrderResponse paymentOrder = paymentService.createPaymentOrder(booking);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "paymentOrder", paymentOrder,
                    "message", "Payment order created successfully"
            ));

        } catch (Exception e) {
            log.error("Failed to create payment order for booking: {}", bookingId, e);
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentData) {
        try {
            String paymentId = paymentData.get("razorpay_payment_id");
            String orderId = paymentData.get("razorpay_order_id");
            String signature = paymentData.get("razorpay_signature");
            String bookingIdStr = paymentData.get("booking_id");

            UUID bookingId = UUID.fromString(bookingIdStr);

            // Verify payment
            boolean isValid = paymentService.verifyPayment(paymentId, orderId, signature);

            if (isValid) {
                // Confirm booking after successful payment
                Booking confirmedBooking = bookingService.confirmBooking(bookingId);

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Payment verified and booking confirmed",
                        "booking", confirmedBooking
                ));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Payment verification failed"));
            }

        } catch (Exception e) {
            log.error("Payment verification error", e);
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Payment verification failed"));
        }
    }
}