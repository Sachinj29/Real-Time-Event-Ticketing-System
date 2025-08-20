package com.example.ticketing.identity.web;

import com.example.ticketing.identity.domain.User;
import com.example.ticketing.identity.repo.UserRepository;
import com.example.ticketing.identity.web.dto.UpdateProfileRequest;
import com.example.ticketing.identity.web.dto.UserProfileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository users;

    @GetMapping
    public ResponseEntity<UserProfileResponse> me(@AuthenticationPrincipal Jwt jwt) {
        Long uid = Long.parseLong(jwt.getSubject());
        User u = users.findById(uid).orElseThrow();
        return ResponseEntity.ok(new UserProfileResponse(u.getId(), u.getEmail(), u.getName(), u.getRoles(), u.getStatus()));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponse> update(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody UpdateProfileRequest req) {
        Long uid = Long.parseLong(jwt.getSubject());
        User u = users.findById(uid).orElseThrow();
        if (req.name() != null) u.setName(req.name());
        users.save(u);
        return ResponseEntity.ok(new UserProfileResponse(u.getId(), u.getEmail(), u.getName(), u.getRoles(), u.getStatus()));
    }
}
