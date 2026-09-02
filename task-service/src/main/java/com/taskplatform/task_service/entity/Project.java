package com.taskplatform.task_service.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "owner_id" , nullable = false)
    private Long ownerId;

    @Column(name = "created_at" , updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    void onCreate(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
    @PreUpdate
    void onUpdate(){
        updatedAt = Instant.now();
    }

}
