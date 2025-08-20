package com.example.ticketing.security.auth;

import org.springframework.stereotype.Service;

/**
 * For brevity, we rely on self-contained JWT refresh tokens.
 * If you want revocation lists, store refresh tokens in DB and validate here.
 */
@Service
public class RefreshTokenService {
    // TODO: implement persistent refresh token store if needed
}
