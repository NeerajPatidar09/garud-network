package com.garud.network.dto;

public record TokenResponse(
    String accessToken,
    String tokenType,
    long expiresIn
) {}
