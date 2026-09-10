package com.taskplatform.task_service.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private Instant createdAt;
    private Instant updatedAt;
}
