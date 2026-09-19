package com.taskplatform.user_service;

import com.taskplatform.user_service.dto.UserRequest;
import com.taskplatform.user_service.dto.UserResponse;
import com.taskplatform.user_service.entity.UserRole;
import com.taskplatform.user_service.exception.DuplicateResourceException;
import com.taskplatform.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserFlowIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser_shouldDefaultRoleAndPersist() {
        UserRequest request = new UserRequest();
        request.setName("Test User");
        request.setEmail("test.user@example.com");
        request.setRole(UserRole.MEMBER);

        UserResponse created = userService.createUser(request);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getEmail()).isEqualTo("test.user@example.com");
        assertThat(created.getRole()).isEqualTo(UserRole.MEMBER);
    }

    @Test
    void createUser_withDuplicateEmail_shouldThrow() {
        UserRequest first = new UserRequest();
        first.setName("First User");
        first.setEmail("duplicate@example.com");
        first.setRole(UserRole.MEMBER);
        userService.createUser(first);

        UserRequest second = new UserRequest();
        second.setName("Second User");
        second.setEmail("duplicate@example.com");
        second.setRole(UserRole.ADMIN);

        assertThatThrownBy(() -> userService.createUser(second))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("duplicate@example.com");
    }
}