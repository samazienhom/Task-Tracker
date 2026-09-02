package com.app.task_tracker.services;

import com.app.task_tracker.domain.entities.TaskListEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface TaskListService {

    List<TaskListEntity> listTaskList();

    TaskListEntity createTaskList(TaskListEntity taskListEntity);

    Optional<TaskListEntity> getTaskList(UUID id);

    TaskListEntity updateTaskList(UUID id,TaskListEntity taskListEntity);

    void deleteTaskList(UUID id);
}
