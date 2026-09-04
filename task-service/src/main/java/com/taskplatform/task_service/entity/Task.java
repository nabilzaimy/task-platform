package com.taskplatform.task_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table (name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @Column(name = "created_at" , updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        if (status == null) {
            status = TaskStatus.TODO;
        }
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
        }

    }
