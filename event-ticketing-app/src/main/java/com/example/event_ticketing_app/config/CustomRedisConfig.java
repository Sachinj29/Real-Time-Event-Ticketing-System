package com.example.event_ticketing_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;

@Configuration
public class CustomRedisConfig {

    @Value("${spring.data.redis.url}")
    private String redisUrl;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Parse the Redis URL
        URI uri = URI.create(redisUrl.replaceFirst("redis://", "http://"));

        String host = uri.getHost();
        int port = uri.getPort();
        String password = uri.getUserInfo().split(":")[1]; // Extract password from "default:password"

        // Create Redis configuration
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(RedisPassword.of(password));

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}