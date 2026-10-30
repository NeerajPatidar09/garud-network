package com.garud.network.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}

@Data
class LoginResponse {
    String token;
    long expiresIn;
    UserInfo user;
}

@Data
class UserInfo {
    Long id;
    String username;
    String email;
    String role;
}
