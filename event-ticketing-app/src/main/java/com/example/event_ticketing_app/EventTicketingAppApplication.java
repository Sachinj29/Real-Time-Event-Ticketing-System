package com.example.event_ticketing_app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCaching
@EnableRetry
public class  EventTicketingAppApplication {

	public static void main(String[] args) {

		System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.3");

		SpringApplication.run(EventTicketingAppApplication.class, args);
		System.out.println("Hello Event-Ticketing-App");

	}

}
