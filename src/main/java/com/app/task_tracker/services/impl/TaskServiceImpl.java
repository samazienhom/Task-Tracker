package com.app.task_tracker.services.impl;

import com.app.task_tracker.domain.entities.TaskEntity;
import com.app.task_tracker.domain.entities.TaskListEntity;
import com.app.task_tracker.domain.entities.TaskPriority;
import com.app.task_tracker.domain.entities.TaskStatus;
import com.app.task_tracker.mappers.TaskMapper;
import com.app.task_tracker.reposoteries.TaskListRepo;
import com.app.task_tracker.reposoteries.TaskRepo;
import com.app.task_tracker.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final TaskListRepo taskListRepo;

    public TaskServiceImpl(TaskRepo taskRepo, TaskMapper taskMapper, TaskListRepo taskListRepo) {
        this.taskRepo = taskRepo;
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<TaskEntity> listTasks(UUID taskListId) {
        if (!taskListRepo.existsById(taskListId)) {
            throw new IllegalArgumentException("task list not available");
        }
        return taskRepo.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public TaskEntity createTask(UUID taskListId, TaskEntity taskEntity) {
        if (null != taskEntity.getId()) {
            throw new IllegalArgumentException("task already have id");
        }
        if (null == taskEntity.getTitle() || taskEntity.getTitle().isBlank()) {
            throw new IllegalArgumentException("task must have a title");
        }
        TaskPriority taskPriority = Optional.ofNullable(taskEntity.getTaskPriority())
                .orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;
        TaskListEntity taskListEntity = taskListRepo.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("invalid task list id"));
        LocalDateTime now = LocalDateTime.now();
        TaskEntity task = new TaskEntity(
                null,
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getDueDate(),
                taskPriority,
                taskStatus,
                taskListEntity,
                now,
                now
        );
        return taskRepo.save(task);
    }

    @Override
    public Optional<TaskEntity> getTask(UUID taskListId, UUID taskId) {
        return taskRepo.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public TaskEntity updateTask(UUID taskListId,
                                 UUID taskId,
                                 TaskEntity taskEntity) {
        if (taskEntity.getId() == null) {
            throw new IllegalArgumentException("Task Must Have An Id");
        }
        if (!Objects.equals(taskId, taskEntity.getId())) {
            throw new IllegalArgumentException("Task Ids Do not Match");
        }
        if (null == taskEntity.getTaskPriority()) {
            throw new IllegalArgumentException("task must have priority");
        }
        if (null == taskEntity.getTaskStatus()) {
            throw new IllegalArgumentException("task must have status");
        }
        TaskEntity task = taskRepo.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("task list not found"));
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setDueDate(taskEntity.getDueDate());
        task.setTaskPriority(taskEntity.getTaskPriority());
        task.setTaskStatus(taskEntity.getTaskStatus());
        task.setUpdatedTime(LocalDateTime.now());
        return taskRepo.save(task);
    }

    @Override
    public TaskEntity partialUpdateTask(UUID taskListId, UUID taskId, TaskEntity taskEntity) {
        return taskRepo.findByTaskListIdAndId(taskListId, taskId)
                .map(task -> {
                    //task.setId(taskId);
                    Optional.ofNullable(taskEntity.getTitle()).ifPresent(task::setTitle);
                    Optional.ofNullable(taskEntity.getDescription()).ifPresent(task::setDescription);
                    Optional.ofNullable(taskEntity.getTaskPriority()).ifPresent(task::setTaskPriority);
                    Optional.ofNullable(taskEntity.getTaskStatus()).ifPresent(task::setTaskStatus);
                    Optional.ofNullable(taskEntity.getDueDate()).ifPresent(task::setDueDate);
                    task.setUpdatedTime(LocalDateTime.now());
                    return taskRepo.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("task list not found"));

    }
    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepo.deleteByTaskListIdAndId(taskListId,taskId);
    }
}
