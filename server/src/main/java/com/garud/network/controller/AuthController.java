package com.garud.network.controller;

import java.time.Duration;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garud.network.dto.AuthResponse;
import com.garud.network.dto.LoginRequest;
import com.garud.network.service.AuthService;
import com.garud.network.service.AuthService.AuthResult;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String REFRESH_COOKIE = "GARUD_REFRESH";

    private final AuthService authService;
    private final boolean cookieSecure;
    private final long refreshTokenDays;

    public AuthController(
        AuthService authService,
        @Value("${garud.security.cookie-secure}") boolean cookieSecure,
        @Value("${garud.security.jwt.refresh-token-days}") long refreshTokenDays
    ) {
        this.authService = authService;
        this.cookieSecure = cookieSecure;
        this.refreshTokenDays = refreshTokenDays;
    }

    @GetMapping("/csrf")
    public ResponseEntity<String> csrf(CsrfToken csrfToken) {

        return ResponseEntity.ok(csrfToken.getToken());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {

        AuthResult result = authService.login(request);

        addRefreshCookie(response, result.refreshToken());

        return ResponseEntity.ok(
            new AuthResponse(
                result.accessToken(),
                "Bearer",
                result.expiresIn()
            )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
        @CookieValue(
            name = REFRESH_COOKIE,
            required = false
        ) String refreshToken,
        HttpServletResponse response
    ) {

        AuthResult result = authService.refresh(refreshToken);

        addRefreshCookie(response, result.refreshToken());

        return ResponseEntity.ok(
            new AuthResponse(
                result.accessToken(),
                "Bearer",
                result.expiresIn()
            )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @CookieValue(
            name = REFRESH_COOKIE,
            required = false
        ) String refreshToken,
        HttpServletResponse response
    ) {

        authService.logout(refreshToken);

        clearRefreshCookie(response);

        return ResponseEntity.noContent().build();
    }

    private void addRefreshCookie(
        HttpServletResponse response,
        String token
    ) {

        ResponseCookie cookie = ResponseCookie
            .from(REFRESH_COOKIE, token)
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite("Strict")
            .path("/api/v1/auth")
            .maxAge(Duration.ofDays(refreshTokenDays))
            .build();

        response.addHeader(
            HttpHeaders.SET_COOKIE,
            cookie.toString()
        );
    }

    private void clearRefreshCookie(
        HttpServletResponse response
    ) {

        ResponseCookie cookie = ResponseCookie
            .from(REFRESH_COOKIE, "")
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite("Strict")
            .path("/api/v1/auth")
            .maxAge(Duration.ZERO)
            .build();

        response.addHeader(
            HttpHeaders.SET_COOKIE,
            cookie.toString()
        );
    }
}
