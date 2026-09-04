package com.taskplatform.task_service.service;

import com.taskplatform.task_service.entity.Project;
import com.taskplatform.task_service.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    public Project updateProject(Long id , Project updatedProject){
        Project existing = getProjectById(id);
        existing.setName(updatedProject.getName());
        existing.setDescription(updatedProject.getDescription());
        existing.setOwnerId(updatedProject.getOwnerId());
        return projectRepository.save(existing);
    }

    public void deleteProject(Long id){
        Project existing = getProjectById(id);
        projectRepository.delete(existing);
    }


}
