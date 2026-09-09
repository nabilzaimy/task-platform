package com.taskplatform.task_service.service;

import com.taskplatform.task_service.dto.TaskRequest;
import com.taskplatform.task_service.dto.TaskResponse;
import com.taskplatform.task_service.entity.Project;
import com.taskplatform.task_service.entity.Task;
import com.taskplatform.task_service.repository.ProjectRepository;
import com.taskplatform.task_service.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponse createTask(Long projectId, TaskRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setAssigneeId(request.getAssigneeId());
        task.setProject(project);

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TaskResponse> getTaskByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        return toResponse(findTaskOrThrow(id));
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task existing = findTaskOrThrow(id);
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setStatus(request.getStatus());
        existing.setAssigneeId(request.getAssigneeId());
        Task saved = taskRepository.save(existing);
        return toResponse(saved);
    }

    public void deleteTask(Long id) {
        Task existing = findTaskOrThrow(id);
        taskRepository.delete(existing);
    }

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    private TaskResponse toResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setAssigneeId(task.getAssigneeId());
        response.setProjectId(task.getProject().getId());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }

}