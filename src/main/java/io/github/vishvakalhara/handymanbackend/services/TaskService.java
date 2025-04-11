package io.github.vishvakalhara.handymanbackend.services;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;

public interface TaskService {

    Task createTask(CreateTaskRequest data);
}
