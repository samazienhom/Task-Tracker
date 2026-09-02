package com.app.task_tracker.services;

import com.app.task_tracker.domain.entities.TaskEntity;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    List<TaskEntity> listTasks(UUID taskListId);

    TaskEntity createTask(UUID taskListId,TaskEntity taskEntity);

    Optional<TaskEntity> getTask(UUID taskListId,UUID taskId);

    TaskEntity updateTask(UUID taskListId,UUID taskId, TaskEntity taskEntity);

    TaskEntity partialUpdateTask(UUID taskListId,UUID taskId, TaskEntity taskEntity);


    void deleteTask(UUID taskListId,UUID taskId);
}
