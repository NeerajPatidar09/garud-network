package com.garud.network.dto;

public record AuthResponse(
    String accessToken,
    String tokenType,
    long expiresIn
) {}
