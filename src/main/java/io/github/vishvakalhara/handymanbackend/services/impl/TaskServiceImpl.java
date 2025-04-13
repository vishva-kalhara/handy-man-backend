package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.aws_s3_storage.S3Service;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.FilterTasksRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.CategoryRepo;
import io.github.vishvakalhara.handymanbackend.repositories.TaskRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.github.vishvakalhara.handymanbackend.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    public final CategoryRepo categoryRepo;

    public final UserRepo userRepo;

    public final TaskRepo taskRepo;

    @Override
    public List<Task> getAllTasks(FilterTasksRequest queryParams) {

        return taskRepo.filterTasks(
                queryParams.getIsEmergency(),
                queryParams.getCreatorId(),
                queryParams.getCategory(),
                queryParams.getTaskStatus(),
                queryParams.getMinPrice(),
                queryParams.getMaxPrice(),
                queryParams.getIsDeleted(),
                PageRequest.of(queryParams.getPage(), queryParams.getSize(), queryParams.getSort())
        );
    }

    @Override
    public Task createTask(CreateTaskRequest data) {

        // Upload image and get the uri
        String imageUri = new S3Service().uploadFile(data.getImage());

        // find category
        Category category = categoryRepo.findById(data.getCategoryId()).orElseThrow(() -> new AppException("Category not found. Please select another category.", HttpStatus.NOT_FOUND));

        // find creator
        User creator = userRepo.findById(data.getCreatorId()).orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));

        // build the task
        Task taskToBeCreated = Task.builder()
                .image(imageUri)
                .title(data.getTitle())
                .description(data.getDescription())
                .maxPrice(data.getMaxPrice())
                .category(category)
                .creator(creator)
                .isEmergency(data.getIsEmergency())
                .build();

        return taskRepo.save(taskToBeCreated);
    }

    @Override
    public Task getOneTask(UUID id) {

        Task task = taskRepo.findById(id).orElseThrow(
                () -> new AppException("Task is Not Found!", HttpStatus.NOT_FOUND)
        );

        if(task.getIsDeleted()){
            throw new AppException("Task is Not Found!", HttpStatus.NOT_FOUND);
        }

        return task;
    }

    @Override
    public void deleteTask(UUID id) {

        Task task = taskRepo.findById(id).orElseThrow(
                () -> new AppException("Task is Not Found!", HttpStatus.NOT_FOUND)
        );

        if(task.getIsDeleted()){
            throw new AppException("Task is Not Found!", HttpStatus.NOT_FOUND);
        }

        taskRepo.delete(task);
    }
}
