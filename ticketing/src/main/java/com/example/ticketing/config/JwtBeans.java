package com.example.ticketing.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.*;

@Configuration
@RequiredArgsConstructor
public class JwtBeans {

    private final JwtConfig jwtConfig;

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = jwtConfig.jwk();
        com.nimbusds.jose.jwk.source.JWKSource<SecurityContext> jks = (selector, ctx) -> java.util.List.of(jwk);
        return new NimbusJwtEncoder(jks);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(new javax.crypto.spec.SecretKeySpec(
                jwtConfig.getSecret().getBytes(), "HmacSHA256")).build();
    }
}
