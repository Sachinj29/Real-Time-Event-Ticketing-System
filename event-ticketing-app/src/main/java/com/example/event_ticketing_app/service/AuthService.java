package com.example.event_ticketing_app.service;

import com.example.event_ticketing_app.entity.User;
import com.example.event_ticketing_app.entity.UserRole;
import com.example.event_ticketing_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(String email, String password, String firstName, String lastName, UserRole role) {
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User already exists with email: " + email);
        }

        // Create new user
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .isVerified(true) // Auto-verify for now
                .isEnabled(true)
                .build();

        userRepository.save(user);

        // Generate and return JWT token
        return jwtService.generateToken(user);
    }

    public String authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }
}
