package com.example.ticketing.payments.razorpay;

import com.example.ticketing.orders.domain.Order;
import com.example.ticketing.orders.repo.OrderRepository;
import com.example.ticketing.orders.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

@RestController
@RequestMapping("/webhooks/razorpay")
@RequiredArgsConstructor
public class WebhookController {

    private final OrderRepository orders;
    private final OrderService orderService;

    @Value("${payments.razorpay.webhook.secret}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<Void> handle(HttpServletRequest request, @RequestHeader("X-Razorpay-Signature") String signature) throws Exception {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) body.append(line);
        }
        if (!verifySignature(body.toString(), signature, webhookSecret)) {
            return ResponseEntity.status(400).build();
        }

        // Simplified: in real, parse JSON, locate order id and event type
        // Assuming providerPaymentId and our order id mapping exists
        // Here, no-op. Extend as needed.

        return ResponseEntity.ok().build();
    }

    private boolean verifySignature(String payload, String expectedSignature, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] digest = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        String hex = HexFormat.of().formatHex(digest);
        return hex.equals(expectedSignature);
    }
}
