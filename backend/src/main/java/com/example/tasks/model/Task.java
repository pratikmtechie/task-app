package com.example.tasks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String assignee;
    private Instant createdAt;
    private Instant updatedAt;

    public Task() {}

    public Task(String title, String description, TaskStatus status, TaskPriority priority, String assignee) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}