package com.example.ticketing.payments.razorpay;

import com.razorpay.RazorpayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayClientFactory {

    @Bean
    public RazorpayClient razorpayClient(
            @Value("${payments.razorpay.key-id}") String keyId,
            @Value("${payments.razorpay.key-secret}") String keySecret
    ) throws Exception {
        return new RazorpayClient(keyId, keySecret);
    }
}
