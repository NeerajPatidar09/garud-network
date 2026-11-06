package com.garud.network.dto;

import org.springframework.stereotype.Component;

import com.garud.network.model.User;

@Component
public class UserMapper {

    public User toEntity(UserCreateRequest request) {

        User user = new User();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        return user;
    }

    public UserResponse toResponse(User user) {

        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.isEnabled(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
