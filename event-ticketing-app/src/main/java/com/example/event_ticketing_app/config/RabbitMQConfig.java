package com.example.event_ticketing_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queue and Exchange names
    public static final String BOOKING_QUEUE = "event.booking.queue";
    public static final String EMAIL_QUEUE = "event.email.queue";
    public static final String NOTIFICATION_QUEUE = "event.notification.queue";
    public static final String EVENT_EXCHANGE = "event.exchange";

    // Routing keys
    public static final String BOOKING_ROUTING_KEY = "event.booking.created";
    public static final String EMAIL_ROUTING_KEY = "event.email.send";
    public static final String NOTIFICATION_ROUTING_KEY = "event.notification.*";

    // Queues
    @Bean
    public Queue bookingQueue() {
        return QueueBuilder.durable(BOOKING_QUEUE)
                .withArgument("x-message-ttl", 600000)
                .build();
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE)
                .withArgument("x-message-ttl", 1800000)
                .build();
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE)
                .withArgument("x-message-ttl", 300000)
                .build();
    }

    // Exchange
    @Bean
    public TopicExchange eventExchange() {
        return ExchangeBuilder.topicExchange(EVENT_EXCHANGE)
                .durable(true)
                .build();
    }

    // Bindings
    @Bean
    public Binding bookingBinding() {
        return BindingBuilder
                .bind(bookingQueue())
                .to(eventExchange())
                .with(BOOKING_ROUTING_KEY);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(eventExchange())
                .with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(eventExchange())
                .with(NOTIFICATION_ROUTING_KEY);
    }

    // Message Converter (Spring Boot will auto-configure RabbitTemplate with this)
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // REMOVED: Custom rabbitTemplate bean - let Spring Boot handle it
}