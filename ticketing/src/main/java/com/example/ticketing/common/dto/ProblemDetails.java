package com.example.ticketing.common.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Map;

@Value
@Builder
public class ProblemDetails {
    String type;
    String title;
    int status;
    String code;
    String detail;
    String traceId;
    Instant timestamp;
    Map<String, Object> extra;
}
