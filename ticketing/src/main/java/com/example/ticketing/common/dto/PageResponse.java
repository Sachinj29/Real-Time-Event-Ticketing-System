package com.example.ticketing.common.dto;

import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value
@Builder
public class PageResponse<T> {
    List<T> content;
    int page;
    int size;
    long totalElements;
    int totalPages;
}
