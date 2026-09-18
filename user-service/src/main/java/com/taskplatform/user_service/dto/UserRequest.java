package com.taskplatform.user_service.dto;

import com.taskplatform.user_service.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private UserRole role;
}