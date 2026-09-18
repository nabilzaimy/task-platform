package com.taskplatform.user_service.service;

import com.taskplatform.user_service.dto.UserRequest;
import com.taskplatform.user_service.dto.UserResponse;
import com.taskplatform.user_service.entity.User;
import com.taskplatform.user_service.exception.DuplicateResourceException;
import com.taskplatform.user_service.exception.ResourceNotFoundException;
import com.taskplatform.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            throw new DuplicateResourceException("Email already in use: " + request.getEmail());
        });

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return toResponse(findUserOrThrow(id));
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = findUserOrThrow(id);
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setRole(request.getRole());
        return toResponse(userRepository.save(existing));
    }

    public void deleteUser(Long id) {
        User existing = findUserOrThrow(id);
        userRepository.delete(existing);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}