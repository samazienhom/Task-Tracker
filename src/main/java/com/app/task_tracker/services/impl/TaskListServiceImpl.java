package com.app.task_tracker.services.impl;


import com.app.task_tracker.domain.entities.TaskEntity;
import com.app.task_tracker.domain.entities.TaskListEntity;
import com.app.task_tracker.reposoteries.TaskListRepo;
import com.app.task_tracker.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepo taskListRepo;

    public TaskListServiceImpl(TaskListRepo taskListRepo) {
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<TaskListEntity> listTaskList() {
        return taskListRepo.findAll();
    }

    @Override
    public TaskListEntity createTaskList(TaskListEntity taskListEntity) {
        if (null != taskListEntity.getId()){
            throw new IllegalArgumentException("Task list Already has Id");
        }
        if (null == taskListEntity.getTitle() || taskListEntity.getTitle().isBlank()){
            throw  new IllegalArgumentException("task title must be present");
        }
        LocalDateTime now=LocalDateTime.now();
        return taskListRepo.save(new TaskListEntity(
                null,
                taskListEntity.getTitle(),
                taskListEntity.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskListEntity> getTaskList(UUID id) {
        return taskListRepo.findById(id);
    }

    @Transactional
    @Override
    public TaskListEntity updateTaskList(UUID id, TaskListEntity taskListEntity) {
        if (!taskListRepo.existsById(id)){
            throw new IllegalArgumentException("task list not found");
        }
        if (taskListEntity.getId() ==null){
            throw new IllegalArgumentException("task list mast have an id");
        }
        if (!Objects.equals(taskListEntity.getId(),id)){
            throw new IllegalArgumentException("Task list id can not be changed");
        }
        TaskListEntity taskList=taskListRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("task list not found"));
        taskList.setId(id);
        taskList.setTitle(taskListEntity.getTitle());
        taskList.setDescription(taskListEntity.getDescription());
        taskList.setUpdatedTime(LocalDateTime.now());
        return taskListRepo.save(taskList);

    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepo.deleteById(id);
    }
}
