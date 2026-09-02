package com.app.task_tracker.controllers;

import com.app.task_tracker.domain.dto.TaskDTO;
import com.app.task_tracker.domain.entities.TaskEntity;
import com.app.task_tracker.mappers.TaskMapper;
import com.app.task_tracker.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/taskLists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDTO> listTasks(@PathVariable("task_list_id")UUID taskListId){
        return taskService.listTasks(taskListId)
                .stream().map(taskMapper::toDto).toList();
    }

    @PostMapping
    public  TaskDTO createTask(@PathVariable("task_list_id") UUID taskListId,@RequestBody TaskDTO taskDTO ){
       TaskEntity taskEntity= taskService.createTask(taskListId,taskMapper.fromDto(taskDTO));
       return taskMapper.toDto(taskEntity);
    }

    @GetMapping("/{task_id}")
    public Optional<TaskDTO> getTask(@PathVariable("task_list_id") UUID taskListId
            , @PathVariable("task_id") UUID taskId){
        return taskService.getTask(taskListId,taskId).map(taskMapper::toDto);
    }

    @PutMapping("/{task_id}")
    public TaskDTO  updateTask(@PathVariable("task_list_id") UUID taskListId,
                               @PathVariable("task_id") UUID taskId,
                               @RequestBody TaskDTO taskDTO){

        TaskEntity taskEntity=taskService.updateTask(taskListId,
                taskId,
                taskMapper.fromDto(taskDTO));
        return taskMapper.toDto(taskEntity);
    }

    @PatchMapping("/{task_id}")
    public TaskDTO  partialUpdateTask(@PathVariable("task_list_id") UUID taskListId,
                               @PathVariable("task_id") UUID taskId,
                               @RequestBody TaskDTO taskDTO){

        TaskEntity taskEntity=taskService.partialUpdateTask(taskListId,
                taskId,
                taskMapper.fromDto(taskDTO));
        return taskMapper.toDto(taskEntity);
    }

    @DeleteMapping("/{task_id}")
    public void deleteTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId
    ){
        taskService.deleteTask(taskListId,taskId);
    }
}
