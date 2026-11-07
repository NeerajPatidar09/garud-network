package com.garud.network.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.garud.network.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final long refreshTokenDays;

    public RefreshTokenService(
        RefreshTokenRepository repository,
        @Value("${garud.security.jwt.refresh-token-days}")
        long refreshTokenDays
    ) {
        this.repository = repository;
        this.refreshTokenDays = refreshTokenDays;
    }

    @Transactional
    public String create(Long userId) {

        byte[] bytes = new byte[32];

        secureRandom.nextBytes(bytes);

        String rawToken = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bytes);

        RefreshToken refreshToken = new RefreshToken(
            userId,
            hash(rawToken),
            Instant.now().plus(refreshTokenDays, ChronoUnit.DAYS)
        );

        repository.save(refreshToken);

        return rawToken;
    }

    @Transactional
    public Long consume(String rawToken) {

        if (rawToken == null || rawToken.isBlank()) {
            throw new ResourceNotFoundException(
                "Invalid refresh token"
            );
        }

        RefreshToken storedToken = repository
            .findByTokenHash(hash(rawToken))
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Invalid refresh token"
                )
            );

        if (storedToken.isRevoked() || storedToken.isExpired()) {
            throw new ResourceNotFoundException(
                "Invalid refresh token"
            );
        }

        storedToken.revoke();

        repository.save(storedToken);

        return storedToken.getUserId();
    }

    @Transactional
    public void revoke(String rawToken) {

        if (rawToken == null || rawToken.isBlank()) {
            return;
        }

        repository.findByTokenHash(hash(rawToken))
            .ifPresent(token -> {
                if (!token.isRevoked()) {
                    token.revoke();
                    repository.save(token);
                }
            });
    }

    private String hash(String value) {

        try {

            MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                value.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (Exception exception) {

            throw new IllegalStateException(
                "Unable to process refresh token"
            );
        }
    }
}
