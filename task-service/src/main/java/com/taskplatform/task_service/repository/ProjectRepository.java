package com.taskplatform.task_service.repository;

import com.taskplatform.task_service.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository <Project , Long>{

}
