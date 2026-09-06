package com.taskplatform.task_service.service;

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

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getTaskByProject(Long projectId){
        return taskRepository.findByProjectId(projectId);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public Task updateTask(Long id , Task updatedTask){
        Task existing = getTaskById(id);
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setStatus(updatedTask.getStatus());
        existing.setAssigneeId(updatedTask.getAssigneeId());
        return taskRepository.save(existing);

    }

    public void deleteTask(Long id) {
        Task existing = getTaskById(id);
        taskRepository.delete(existing);
    }
}
