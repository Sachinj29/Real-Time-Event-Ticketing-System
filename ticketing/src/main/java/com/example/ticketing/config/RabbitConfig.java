package com.example.ticketing.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EX_DOMAIN = "ticketing.domain";
    public static final String EX_NOTIFY = "ticketing.notifications";
    public static final String Q_EMAIL = "email-delivery";
    public static final String Q_OUTBOX = "outbox-processor";
    public static final String Q_WEBHOOK_RETRY = "webhook-retry";

    @Bean
    public TopicExchange domainExchange() {
        return new TopicExchange(EX_DOMAIN, true, false);
    }

    @Bean
    public TopicExchange notifyExchange() {
        return new TopicExchange(EX_NOTIFY, true, false);
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(Q_EMAIL).build();
    }

    @Bean
    public Queue outboxQueue() {
        return QueueBuilder.durable(Q_OUTBOX).build();
    }

    @Bean
    public Queue webhookRetryQueue() {
        return QueueBuilder.durable(Q_WEBHOOK_RETRY).build();
    }

    @Bean
    public Binding bindEmail() {
        return BindingBuilder.bind(emailQueue()).to(notifyExchange()).with("notify.email.*");
    }
}
