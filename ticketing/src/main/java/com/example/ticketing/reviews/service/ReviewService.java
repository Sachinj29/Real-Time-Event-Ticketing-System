package com.example.ticketing.reviews.service;

import com.example.ticketing.reviews.domain.ReviewDocument;
import com.example.ticketing.reviews.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviews;

    public ReviewDocument create(ReviewDocument d) { return reviews.save(d); }
    public ReviewDocument update(ReviewDocument d) { d.setUpdatedAt(Instant.now()); return reviews.save(d); }
    public void delete(String id) { reviews.deleteById(id); }
    public List<ReviewDocument> forEvent(Long eventId) { return reviews.findByEventId(eventId); }
    public List<ReviewDocument> forUser(Long userId) { return reviews.findByUserId(userId); }
}
