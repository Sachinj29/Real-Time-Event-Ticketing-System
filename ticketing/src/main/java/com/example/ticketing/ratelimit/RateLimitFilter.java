package com.example.ticketing.ratelimit;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateLimitFilter implements Filter {

    private final InMemoryRateLimiter limiter;

    public RateLimitFilter(
            @Value("${app.ratelimit.login.capacity:10}") long capacity,
            @Value("${app.ratelimit.login.refill-per-minute:10}") long refill
    ) {
        this.limiter = new InMemoryRateLimiter(capacity, refill);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getRequestURI().startsWith("/auth/login")) {
            String key = req.getRemoteAddr();
            if (!limiter.allow(key)) {
                resp.setStatus(429);
                resp.getWriter().write("Too Many Requests");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
