package com.example.ticketing.security.jwt;

import com.example.ticketing.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtEncoder encoder;
    private final JwtConfig config;

    public String issueAccessToken(String subject, String role) {
        Instant now = Instant.now();
        Instant exp = now.plus(parseDurationMinutes(config.getAccessTtl()), ChronoUnit.MINUTES);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(config.getIssuer())
                .issuedAt(now)
                .expiresAt(exp)
                .subject(subject)
                .claim("role", role)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String issueRefreshToken(String subject) {
        Instant now = Instant.now();
        Instant exp = now.plus(parseDurationDays(config.getRefreshTtl()), ChronoUnit.DAYS);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(config.getIssuer())
                .issuedAt(now)
                .expiresAt(exp)
                .subject(subject)
                .claim("type", "refresh")
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private long parseDurationMinutes(String s) {
        if (s.endsWith("m")) return Long.parseLong(s.replace("m",""));
        if (s.endsWith("h")) return Long.parseLong(s.replace("h","")) * 60;
        if (s.endsWith("d")) return Long.parseLong(s.replace("d","")) * 1440;
        return Long.parseLong(s);
    }

    private long parseDurationDays(String s) {
        if (s.endsWith("d")) return Long.parseLong(s.replace("d",""));
        if (s.endsWith("h")) return Long.parseLong(s.replace("h","")) / 24;
        if (s.endsWith("m")) return Long.parseLong(s.replace("m","")) / 1440;
        return Long.parseLong(s);
    }
}
