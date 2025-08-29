//package com.example.event_ticketing_app.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    } // [1][2]
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    } // [1][2]
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(AbstractHttpConfigurer::disable)
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .authorizeHttpRequests(auth -> auth
////                        // Auth endpoints public
////                        .requestMatchers("/api/auth/**").permitAll()
////                        // Swagger UI and OpenAPI docs public
////                        .requestMatchers(
////                                "/swagger-ui/**",
////                                "/swagger-ui.html",
////                                "/v3/api-docs/**",
////                                "/v3/api-docs.yaml"
////                        ).permitAll()
////                        // Optional: actuator if used
////                        .requestMatchers("/actuator/**").permitAll()
////                        // Everything else requires auth
////                        .anyRequest().authenticated()
////                );
////        return http.build();
////    } // [1][2]
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/v3/api-docs/",
//                                "/swagger-ui.html",
//                                "/swagger-ui/",
//                                "/swagger-resources/",
//                                "/webjars/"
//                        ).permitAll()
//                        .requestMatchers("/api/auth/").permitAll()
//                        .requestMatchers("/api/events/").permitAll()
//                        .requestMatchers("/api/bookings/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }
//}


package com.example.event_ticketing_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Permit all requests for Swagger UI and API docs (corrected paths)
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        // Permit your authentication and other public endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/events/**").permitAll()
                        .requestMatchers("/api/bookings/**").permitAll()
                        .requestMatchers("/api/payments/**").permitAll()
                        .requestMatchers("/payment-test.html/**").permitAll()
                        // Any other requests require authentication
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
