package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;

import java.util.UUID;

public interface TaskService {

    Task createTask(CreateTaskRequest data);

    Task getOneTask(UUID id);
}
