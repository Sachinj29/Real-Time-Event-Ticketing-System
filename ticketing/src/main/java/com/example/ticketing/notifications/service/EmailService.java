package com.example.ticketing.notifications.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void send(String to, String subject, String htmlBody) {
        // Replace with real provider (e.g., Resend/Mailgun). For dev, just log.
        log.info("Sending email to={}, subject={}\n{}", to, subject, htmlBody);
    }
}
