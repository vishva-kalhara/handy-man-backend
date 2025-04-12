package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.FilterTasksRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    List<Task> getAllTasks(FilterTasksRequest queryParams);

    Task createTask(CreateTaskRequest data);

    Task getOneTask(UUID id);

    void deleteTask(UUID id);
}
