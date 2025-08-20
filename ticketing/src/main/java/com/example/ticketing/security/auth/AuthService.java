package com.example.ticketing.security.auth;

import com.example.ticketing.identity.domain.User;
import com.example.ticketing.identity.repo.UserRepository;
import com.example.ticketing.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository users;
    private final JwtTokenService tokens;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(String email, String rawPassword, String name) {
        users.findByEmail(email).ifPresent(u -> { throw new IllegalArgumentException("Email already registered"); });
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(rawPassword));
        u.setName(name);
        u.setRoles("USER");
        u.setStatus("ACTIVE");
        return users.save(u);
    }

    public Optional<User> authenticate(String email, String rawPassword) {
        return users.findByEmail(email)
                .filter(u -> encoder.matches(rawPassword, u.getPasswordHash()));
    }

    public String issueAccess(User u) {
        String role = Optional.ofNullable(u.getRoles()).orElse("USER");
        return tokens.issueAccessToken(String.valueOf(u.getId()), role);
    }

    public String issueRefresh(User u) {
        return tokens.issueRefreshToken(String.valueOf(u.getId()));
    }
}
