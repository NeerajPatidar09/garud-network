package com.garud.network.dto;

import java.time.Instant;

public record UserResponse(

    Long id,
    String username,
    String email,
    String firstName,
    String lastName,
    boolean enabled,
    Instant createdAt,
    Instant updatedAt

) {}
