package com.example.ticketing.security.auth;

import com.example.ticketing.identity.domain.User;
import com.example.ticketing.security.auth.dto.LoginRequest;
import com.example.ticketing.security.auth.dto.RegisterRequest;
import com.example.ticketing.security.auth.dto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest req) {
        User u = authService.register(req.email(), req.password(), req.name());
        String access = authService.issueAccess(u);
        String refresh = authService.issueRefresh(u);
        return ResponseEntity.ok(TokenResponse.bearer(access, refresh));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        User u = authService.authenticate(req.email(), req.password())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        String access = authService.issueAccess(u);
        String refresh = authService.issueRefresh(u);
        return ResponseEntity.ok(TokenResponse.bearer(access, refresh));
    }
}
