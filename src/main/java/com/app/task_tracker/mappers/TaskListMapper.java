package com.app.task_tracker.mappers;

import com.app.task_tracker.domain.dto.TaskListDTO;
import com.app.task_tracker.domain.entities.TaskListEntity;

public interface TaskListMapper {

    TaskListEntity fromDto(TaskListDTO tasklistDTO);

    TaskListDTO toDto(TaskListEntity tasklistEntity);
}
