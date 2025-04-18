package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.FilterTasksRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<Task> getAllTasks(FilterTasksRequest queryParams);

    Task createTask(CreateTaskRequest data);

    Task getOneTask(UUID id);

    List<Task> getMyTasks(UUID userId, Pageable pageable);

    Task completeTask(UUID taskId, UUID creatorId);

    void deleteTask(UUID id);
}
