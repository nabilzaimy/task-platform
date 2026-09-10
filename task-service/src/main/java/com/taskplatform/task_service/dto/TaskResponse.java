package com.taskplatform.task_service.dto;

import com.taskplatform.task_service.entity.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long projectId;
    private Instant createdAt;
    private Instant updatedAt;
}