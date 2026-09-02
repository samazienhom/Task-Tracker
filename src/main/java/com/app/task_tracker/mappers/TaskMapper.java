package com.app.task_tracker.mappers;

import com.app.task_tracker.domain.dto.TaskDTO;
import com.app.task_tracker.domain.entities.TaskEntity;

public interface TaskMapper {

    TaskEntity fromDto(TaskDTO taskDTO);

    TaskDTO toDto(TaskEntity taskEntity);
}
