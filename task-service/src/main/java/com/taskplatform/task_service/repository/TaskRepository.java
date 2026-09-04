package com.taskplatform.task_service.repository;

import com.taskplatform.task_service.entity.Task;
import com.taskplatform.task_service.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long ProjectdId);

    List<Task> findByProjectIdAndStatus(Long ProjectdId , TaskStatus status);

    List<Task> findByAssigneeId(Long assigneeId);


}
