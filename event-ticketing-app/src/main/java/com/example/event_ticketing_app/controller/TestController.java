package com.example.event_ticketing_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<Map<String, String>> publicEndpoint() {
        return ResponseEntity.ok(Map.of(
                "message", "This is a public endpoint - no authentication required"
        ));
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> userEndpoint(Principal principal) {
        return ResponseEntity.ok(Map.of(
                "message", "Hello " + principal.getName() + "! This is a protected endpoint",
                "user", principal.getName()
        ));
    }

    @GetMapping("/organizer")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<Map<String, String>> organizerEndpoint(Principal principal) {
        return ResponseEntity.ok(Map.of(
                "message", "Hello Organizer " + principal.getName() + "!",
                "role", "ORGANIZER"
        ));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> adminEndpoint(Principal principal) {
        return ResponseEntity.ok(Map.of(
                "message", "Hello Admin " + principal.getName() + "!",
                "role", "ADMIN"
        ));
    }
}