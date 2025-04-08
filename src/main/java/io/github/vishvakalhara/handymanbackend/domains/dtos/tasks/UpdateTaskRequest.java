package io.github.vishvakalhara.handymanbackend.domains.dtos.tasks;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;

public class UpdateTaskRequest {

    private TaskStatus taskStatus;
    private Boolean isDeleted;
}
