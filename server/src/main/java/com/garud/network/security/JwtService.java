package com.garud.network.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final String issuer;
    private final long accessTokenMinutes;

    public JwtService(
        @Value("${garud.security.jwt.secret}") String secret,
        @Value("${garud.security.jwt.issuer}") String issuer,
        @Value("${garud.security.jwt.access-token-minutes}") long accessTokenMinutes
    ) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                "JWT secret must contain at least 32 bytes"
            );
        }

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.issuer = issuer;
        this.accessTokenMinutes = accessTokenMinutes;
    }

    public String generateAccessToken(String username) {

        Instant now = Instant.now();

        return Jwts.builder()
            .subject(username)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(
                Date.from(now.plus(accessTokenMinutes, ChronoUnit.MINUTES))
            )
            .id(java.util.UUID.randomUUID().toString())
            .claim("token_use", "access")
            .signWith(signingKey)
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(
        String token,
        String expectedUsername
    ) {

        Claims claims = extractClaims(token);

        return expectedUsername.equals(claims.getSubject())
            && "access".equals(claims.get("token_use", String.class))
            && claims.getExpiration().after(new Date());
    }

    private Claims extractClaims(String token) {

        return Jwts.parser()
            .verifyWith(signingKey)
            .requireIssuer(issuer)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
