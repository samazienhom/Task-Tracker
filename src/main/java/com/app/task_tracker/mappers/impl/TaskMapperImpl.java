package com.app.task_tracker.mappers.impl;

import com.app.task_tracker.domain.dto.TaskDTO;
import com.app.task_tracker.domain.entities.TaskEntity;
import com.app.task_tracker.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskEntity fromDto(TaskDTO taskDTO) {
        return new TaskEntity(
                taskDTO.id(),
                taskDTO.title(),
                taskDTO.description(),
                taskDTO.dueDate(),
                taskDTO.taskPriority(),
                taskDTO.taskStatus(),
                null,
                null,
                null
        );
    }

    @Override
    public TaskDTO toDto(TaskEntity taskEntity) {
        return new TaskDTO(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getDueDate(),
                taskEntity.getTaskStatus(),
                taskEntity.getTaskPriority()
        );
    }
}
