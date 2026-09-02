package com.app.task_tracker.domain.dto;

import com.app.task_tracker.domain.entities.TaskPriority;
import com.app.task_tracker.domain.entities.TaskStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDTO (
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus taskStatus,
        TaskPriority taskPriority
)
{}