package com.example.ticketing.notifications.queue;

import com.example.ticketing.notifications.domain.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.example.ticketing.config.RabbitConfig.EX_NOTIFY;

@Component
public class EmailProducer {
    private final RabbitTemplate rabbitTemplate;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTicketEmail(EmailMessage msg) {
        rabbitTemplate.convertAndSend(EX_NOTIFY, "notify.email.ticket_issued", msg);
    }
}
