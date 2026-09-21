package com.taskplatform.notification_service.dto;

import lombok.Data;

@Data
public class TaskEventDto {
    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private String status;
    private Long assigneeId;
}