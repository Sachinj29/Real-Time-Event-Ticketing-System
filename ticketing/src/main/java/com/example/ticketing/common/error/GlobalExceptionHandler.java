package com.example.ticketing.common.error;

import com.example.ticketing.common.dto.ProblemDetails;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetails> handleAccessDenied(AccessDeniedException ex) {
        return build(HttpStatus.FORBIDDEN, ErrorCodes.ACCESS_FORBIDDEN, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetails> handleValidation(MethodArgumentNotValidException ex) {
        return build(HttpStatus.BAD_REQUEST, ErrorCodes.VALIDATION_FAILED, "Validation failed", Map.of("errors", ex.getBindingResult().toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetails> handleConstraint(ConstraintViolationException ex) {
        return build(HttpStatus.BAD_REQUEST, ErrorCodes.VALIDATION_FAILED, ex.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetails> handleIllegalArg(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ErrorCodes.VALIDATION_FAILED, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ex.getMessage(), null);
    }

    private ResponseEntity<ProblemDetails> build(HttpStatus status, String code, String detail, Map<String, Object> extra) {
        String traceId = UUID.randomUUID().toString();
        ProblemDetails body = ProblemDetails.builder()
                .type("about:blank")
                .title(status.getReasonPhrase())
                .status(status.value())
                .code(code)
                .detail(detail)
                .traceId(traceId)
                .timestamp(Instant.now())
                .extra(extra)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
