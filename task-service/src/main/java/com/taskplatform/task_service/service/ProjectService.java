package com.taskplatform.task_service.service;

import com.taskplatform.task_service.dto.ProjectRequest;
import com.taskplatform.task_service.dto.ProjectResponse;
import com.taskplatform.task_service.entity.Project;
import com.taskplatform.task_service.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwnerId(request.getOwnerId());

        Project saved = projectRepository.save(project);
        return toResponse(saved);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = findProjectOrThrow(id);
        return toResponse(project);
    }

    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project existing = findProjectOrThrow(id);
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setOwnerId(request.getOwnerId());
        Project saved = projectRepository.save(existing);
        return toResponse(saved);
    }

    public void deleteProject(Long id) {
        Project existing = findProjectOrThrow(id);
        projectRepository.delete(existing);
    }

    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    private ProjectResponse toResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setOwnerId(project.getOwnerId());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        return response;
    }

}