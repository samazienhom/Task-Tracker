package com.app.task_tracker.mappers.impl;

import com.app.task_tracker.domain.dto.TaskListDTO;
import com.app.task_tracker.domain.entities.TaskEntity;
import com.app.task_tracker.domain.entities.TaskListEntity;
import com.app.task_tracker.domain.entities.TaskStatus;
import com.app.task_tracker.mappers.TaskListMapper;
import com.app.task_tracker.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskListEntity fromDto(TaskListDTO tasklistDTO) {
        return new TaskListEntity(
                tasklistDTO.id(),
                tasklistDTO.title(),
                tasklistDTO.description(),
                Optional.ofNullable(tasklistDTO.tasks())
                        .map(taskDTOS -> taskDTOS.stream()
                                .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(
                                null
                        ),
                null,
                null
        );
    }

    @Override
    public TaskListDTO toDto(TaskListEntity tasklistEntity) {
        return new TaskListDTO(
                tasklistEntity.getId(),
                tasklistEntity.getTitle(),
                tasklistEntity.getDescription(),
                Optional.ofNullable(tasklistEntity.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTaskListProgress(tasklistEntity.getTasks()),
                Optional.ofNullable(tasklistEntity.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::toDto).toList())
                        .orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<TaskEntity> tasks) {
        if (null == tasks) {
            return null;
        }
        long closedTaskCount = tasks.stream().filter(task ->
                        TaskStatus.CLOSE == task.getTaskStatus())
                .count();

        return (double) closedTaskCount / tasks.size();
    }
}
