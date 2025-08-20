package com.example.ticketing.notifications.queue;

import com.example.ticketing.notifications.domain.EmailMessage;
import com.example.ticketing.notifications.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.ticketing.config.RabbitConfig.Q_EMAIL;

@Component
public class EmailConsumer {
    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = Q_EMAIL)
    public void consume(EmailMessage msg) {
        emailService.send(msg.getTo(), msg.getSubject(), msg.getHtmlBody());
    }
}
