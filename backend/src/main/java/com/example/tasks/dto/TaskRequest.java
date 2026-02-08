package com.example.tasks.dto;

import com.example.tasks.model.TaskPriority;
import com.example.tasks.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500) String description,
        TaskStatus status,
        TaskPriority priority,
        String assignee
) {}