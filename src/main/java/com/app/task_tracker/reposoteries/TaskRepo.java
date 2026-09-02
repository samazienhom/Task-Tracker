package com.app.task_tracker.reposoteries;

import com.app.task_tracker.domain.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepo  extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByTaskListId(UUID taskListId);

    Optional<TaskEntity> findByTaskListIdAndId(UUID taskListId, UUID id);

    void deleteByTaskListIdAndId(UUID taskListId,UUID id);
}
