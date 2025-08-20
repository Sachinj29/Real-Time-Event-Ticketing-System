package com.example.ticketing.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {
    @Value("${security.jwt.algorithm:HS256}")
    private String algorithm;

    @Value("${security.jwt.secret:change-this-dev-secret-at-least-32-bytes}")
    private String secret;

    @Value("${security.jwt.issuer:event-ticketing}")
    private String issuer;

    @Value("${security.jwt.access-token-ttl:15m}")
    private String accessTtl;

    @Value("${security.jwt.refresh-token-ttl:7d}")
    private String refreshTtl;

    public JWK jwk() {
        return new OctetSequenceKey.Builder(secret.getBytes()).build();
    }
}
