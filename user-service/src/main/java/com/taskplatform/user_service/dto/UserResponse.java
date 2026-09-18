package com.taskplatform.user_service.dto;

import com.taskplatform.user_service.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
}