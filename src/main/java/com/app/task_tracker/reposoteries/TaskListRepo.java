package com.app.task_tracker.reposoteries;

import com.app.task_tracker.domain.entities.TaskListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepo extends JpaRepository<TaskListEntity, UUID> {
}
