package com.taskplatform.task_service.service;

import com.taskplatform.task_service.entity.Project;
import com.taskplatform.task_service.entity.Task;
import com.taskplatform.task_service.repository.ProjectRepository;
import com.taskplatform.task_service.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository , ProjectRepository projectRepository){

        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Task createTask(Long projectId , Task task){

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        task.setProject(project);
        return taskRepository.save(task);
    }
}
