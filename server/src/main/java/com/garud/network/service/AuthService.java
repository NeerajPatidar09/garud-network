package com.garud.network.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.garud.network.dto.LoginRequest;
import com.garud.network.expection.InvalidRefreshTokenException;
import com.garud.network.repository.UserRepository;
import com.garud.network.security.JwtService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final long accessTokenMinutes;

    public AuthService(
        AuthenticationManager authenticationManager,
        UserRepository userRepository,
        UserDetailsService userDetailsService,
        JwtService jwtService,
        RefreshTokenService refreshTokenService,
        @Value("${garud.security.jwt.access-token-minutes}")
        long accessTokenMinutes
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.accessTokenMinutes = accessTokenMinutes;
    }

    public AuthResult login(LoginRequest request) {

        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );

        String username = authentication.getName();

        var user = userRepository.findByUsername(username)
            .orElseThrow(InvalidRefreshTokenException::new);

        return issueTokens(user.getId(), username);
    }

    @Transactional
    public AuthResult refresh(String rawRefreshToken) {

        Long userId = refreshTokenService.consume(rawRefreshToken);

        var user = userRepository.findById(userId)
            .orElseThrow(InvalidRefreshTokenException::new);

        if (!user.isEnabled() || !user.isAccountNonLocked()) {
            throw new InvalidRefreshTokenException();
        }

        UserDetails userDetails =
            userDetailsService.loadUserByUsername(user.getUsername());

        return issueTokens(userId, userDetails.getUsername());
    }

    public void logout(String rawRefreshToken) {
        refreshTokenService.revoke(rawRefreshToken);
    }

    private AuthResult issueTokens(Long userId, String username) {

        String accessToken =
            jwtService.generateAccessToken(username);

        String refreshToken =
            refreshTokenService.create(userId);

        return new AuthResult(
            accessToken,
            refreshToken,
            accessTokenMinutes * 60
        );
    }

    public record AuthResult(
        String accessToken,
        String refreshToken,
        long expiresIn
    ) {}
}
