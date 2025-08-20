package com.example.ticketing.reviews.web;

import com.example.ticketing.reviews.domain.ReviewDocument;
import com.example.ticketing.reviews.service.ReviewService;
import com.example.ticketing.reviews.web.dto.CreateReviewRequest;
import com.example.ticketing.reviews.web.dto.UpdateReviewRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviews;

    @PostMapping
    public ResponseEntity<ReviewDocument> create(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CreateReviewRequest req) {
        Long userId = Long.parseLong(jwt.getSubject());
        ReviewDocument d = new ReviewDocument();
        d.setEventId(req.eventId());
        d.setUserId(userId);
        d.setRating(req.rating());
        d.setText(req.text());
        return ResponseEntity.ok(reviews.create(d));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDocument> update(@PathVariable String id, @Valid @RequestBody UpdateReviewRequest req) {
        ReviewDocument d = new ReviewDocument();
        d.setId(id);
        d.setRating(req.rating());
        d.setText(req.text());
        return ResponseEntity.ok(reviews.update(d));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<ReviewDocument>> byEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(reviews.forEvent(eventId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reviews.delete(id);
        return ResponseEntity.noContent().build();
    }
}
