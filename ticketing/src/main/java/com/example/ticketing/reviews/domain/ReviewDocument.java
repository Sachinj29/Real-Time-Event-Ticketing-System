package com.example.ticketing.reviews.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "reviews")
public class ReviewDocument {
    @Id
    private String id;
    private Long eventId;
    private Long userId;
    private int rating; // 1-5
    private String text;
    private List<String> images;
    private String status = "VISIBLE";
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
