package com.example.tasks.dto;

import com.example.tasks.model.TaskPriority;
import com.example.tasks.model.TaskStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TaskResponse(
        String id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        String assignee,
        Instant createdAt,
        Instant updatedAt
) {}