package com.taskplatform.task_service.controller;

import com.taskplatform.task_service.entity.Project;
import com.taskplatform.task_service.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        Project created = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id , @RequestBody Project project){
        return projectService.updateProject(id,project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
