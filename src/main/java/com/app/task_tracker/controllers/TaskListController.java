package com.app.task_tracker.controllers;

import com.app.task_tracker.domain.dto.TaskListDTO;
import com.app.task_tracker.domain.entities.TaskEntity;
import com.app.task_tracker.domain.entities.TaskListEntity;
import com.app.task_tracker.mappers.TaskListMapper;
import com.app.task_tracker.services.TaskListService;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/taskLists")
public class TaskListController {

    private final TaskListService taskListService;
    public final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDTO> listTaskLists(){
        List<TaskListEntity> taskListEntities=taskListService.listTaskList();
        return taskListEntities.stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDTO createTaskList(@RequestBody TaskListDTO taskListDTO){
        TaskListEntity taskListEntity= taskListMapper.fromDto(taskListDTO);
        TaskListEntity taskListEntitySaved= taskListService.createTaskList(taskListEntity);
        return taskListMapper.toDto(taskListEntitySaved);
    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDTO> getTaskList(@PathVariable("task_list_id") UUID id){
        Optional<TaskListEntity> taskListEntity=taskListService.getTaskList(id);
        return taskListEntity.map(taskListMapper::toDto);
    }

    @PutMapping("/{task_list_id}")
    public TaskListDTO updateTaskList(@PathVariable("task_list_id") UUID id,
                                      @RequestBody TaskListDTO taskListDTO){
        TaskListEntity taskList=taskListMapper.fromDto(taskListDTO);
        TaskListEntity taskListEntity=taskListService.updateTaskList(id,taskList);
        return taskListMapper.toDto(taskListEntity);
    }

    @DeleteMapping("/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID id){
        taskListService.deleteTaskList(id);
    }

}