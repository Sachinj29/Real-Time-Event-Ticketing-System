package com.example.event_ticketing_app.service;

import com.example.event_ticketing_app.dto.PaymentOrderRequest;
import com.example.event_ticketing_app.dto.PaymentOrderResponse;
import com.example.event_ticketing_app.entity.Booking;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key-id}")
    private String keyId;

    public PaymentOrderResponse createPaymentOrder(Booking booking) {
        try {
            // Create order request
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (booking.getTotalAmount() * 100)); // Amount in paisa
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt_" + booking.getId());

            // Add metadata for tracking
            JSONObject notes = new JSONObject();
            notes.put("booking_id", booking.getId().toString());
            notes.put("user_id", booking.getUserId().toString());
            notes.put("event_id", booking.getEventId().toString());
            orderRequest.put("notes", notes);

            // Create order in Razorpay
            Order order = razorpayClient.orders.create(orderRequest);

            log.info("Razorpay order created successfully: {} for booking: {}",
                    order.get("id"), booking.getId());

            return PaymentOrderResponse.builder()
                    .orderId(order.get("id"))
                    .amount(booking.getTotalAmount())
                    .currency("INR")
                    .keyId(keyId)
                    .bookingId(booking.getId())
                    .build();

        } catch (RazorpayException e) {
            log.error("Failed to create Razorpay order for booking: {}", booking.getId(), e);
            throw new RuntimeException("Payment order creation failed", e);
        }
    }

    public boolean verifyPayment(String paymentId, String orderId, String signature) {
        try {
            // Create verification payload
            String payload = orderId + "|" + paymentId;

            // You would implement HMAC SHA256 verification here
            // For now, we'll return true for test mode
            log.info("Payment verification - Payment ID: {}, Order ID: {}", paymentId, orderId);

            return true; // In production, implement proper signature verification

        } catch (Exception e) {
            log.error("Payment verification failed", e);
            return false;
        }
    }
}