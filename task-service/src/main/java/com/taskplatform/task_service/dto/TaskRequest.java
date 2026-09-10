package com.taskplatform.task_service.dto;

import com.taskplatform.task_service.entity.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
}