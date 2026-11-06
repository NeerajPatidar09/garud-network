package com.garud.network.dto;

public record LoginRequest(
    String username,
    String password
) {}