package com.example.ticketing.admin.web;

import com.example.ticketing.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService admin;

    @PostMapping("/audit")
    public ResponseEntity<Void> audit(@AuthenticationPrincipal Jwt jwt,
                                      @RequestParam String action,
                                      @RequestParam String entity,
                                      @RequestParam String entityId) {
        Long actorId = Long.parseLong(jwt.getSubject());
        admin.log(actorId, action, entity, entityId, "{}");
        return ResponseEntity.ok().build();
    }
}
