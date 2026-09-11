package com.taskplatform.task_service;

import com.taskplatform.task_service.dto.ProjectRequest;
import com.taskplatform.task_service.dto.ProjectResponse;
import com.taskplatform.task_service.dto.TaskRequest;
import com.taskplatform.task_service.dto.TaskResponse;
import com.taskplatform.task_service.entity.TaskStatus;
import com.taskplatform.task_service.service.ProjectService;
import com.taskplatform.task_service.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectTaskFlowIntegrationTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Test
    void createProject_thenCreateTaskUnderIt_shouldLinkCorrectly() {
        // 1. Create a project
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("Test Project");
        projectRequest.setDescription("Created during integration test");
        projectRequest.setOwnerId(1L);

        ProjectResponse project = projectService.createProject(projectRequest);

        assertThat(project.getId()).isNotNull();
        assertThat(project.getName()).isEqualTo("Test Project");

        // 2. Create a task under that project
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Verifying the link works");
        taskRequest.setAssigneeId(2L);

        TaskResponse task = taskService.createTask(project.getId(), taskRequest);

        assertThat(task.getId()).isNotNull();
        assertThat(task.getStatus()).isEqualTo(TaskStatus.TODO); // default from @PrePersist
        assertThat(task.getProjectId()).isEqualTo(project.getId());
    }

}