package com.garud.network.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.garud.network.model.RefreshToken;
public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void deleteAllByUserId(Long userId);
}
