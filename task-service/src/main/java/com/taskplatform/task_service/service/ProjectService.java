package com.taskplatform.task_service.service;

import com.taskplatform.task_service.dto.ProjectRequest;
import com.taskplatform.task_service.dto.ProjectResponse;
import com.taskplatform.task_service.entity.Project;
import com.taskplatform.task_service.repository.ProjectRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwnerId(request.getOwnerId());

        Project savedProject = projectRepository.save(project);
        return mapToResponse(savedProject);
    }

    // Cache the single project response by ID using Hazelcast
    @Cacheable(value = "projects", key = "#id")
    public ProjectResponse getProjectById(Long id) {
        System.out.println("--> [DB HIT] Fetching project from PostgreSQL database for ID: " + id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return mapToResponse(project);
    }

    // Update project and clear cache for this ID
    @CacheEvict(value = "projects", key = "#id")
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwnerId(request.getOwnerId());

        Project updatedProject = projectRepository.save(project);
        return mapToResponse(updatedProject);
    }

    // Evict cache entry when project is deleted
    @CacheEvict(value = "projects", key = "#id")
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    // Helper method to convert Project entity to ProjectResponse DTO
    private ProjectResponse mapToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setOwnerId(project.getOwnerId());
        return response;
    }
}