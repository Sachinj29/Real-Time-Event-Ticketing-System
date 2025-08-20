package com.example.ticketing.reviews.repo;

import com.example.ticketing.reviews.domain.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<ReviewDocument, String> {
    List<ReviewDocument> findByEventId(Long eventId);
    List<ReviewDocument> findByUserId(Long userId);
}
