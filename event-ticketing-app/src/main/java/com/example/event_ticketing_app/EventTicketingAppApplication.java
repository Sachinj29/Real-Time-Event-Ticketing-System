package com.example.event_ticketing_app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class  EventTicketingAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(EventTicketingAppApplication.class, args);
		System.out.println("Hello Event-Ticketing-App");

	}

}
