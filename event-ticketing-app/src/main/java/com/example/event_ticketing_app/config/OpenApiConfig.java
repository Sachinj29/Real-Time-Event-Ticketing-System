package com.example.event_ticketing_app.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eventTicketingOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Event Ticketing API")
                        .description("API documentation for Event Ticketing App")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com")
                                .url("https://your-website.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project GitHub")
                        .url("https://github.com/your-repo"));
    }
}
