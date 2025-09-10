//package com.example.event_ticketing_app.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RedissonConfig {
//
//    @Value("${spring.data.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.data.redis.port}")
//    private int redisPort;
//
//    @Value("${spring.data.redis.password}")
//    private String redisPassword;
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//
//        // For Redis Cloud with SSL
//        String address = "rediss://" + redisHost + ":" + redisPort;
//
//        config.useSingleServer()
//                .setAddress(address)
//                .setPassword(redisPassword)
//                .setConnectTimeout(10000)
//                .setTimeout(10000)
//                .setRetryAttempts(3)
//                .setRetryInterval(3000);
//
//        return Redisson.create(config);
//    }
//}


package com.example.event_ticketing_app.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.url}")
    private String redisUrl;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        config.useSingleServer()
                .setAddress(redisUrl)  // Will use IP address now
                .setConnectTimeout(30000)
                .setTimeout(30000)
                .setRetryAttempts(5)
                .setRetryInterval(3000);

        return Redisson.create(config);
    }

}
