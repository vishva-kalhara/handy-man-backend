package io.github.vishvakalhara.handymanbackend.services.impl;

import io.github.vishvakalhara.handymanbackend.aws_s3_storage.ResourceType;
import io.github.vishvakalhara.handymanbackend.aws_s3_storage.S3Service;
import io.github.vishvakalhara.handymanbackend.domains.BidStatus;
import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.FilterTasksRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.*;
import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import io.github.vishvakalhara.handymanbackend.repositories.*;
import io.github.vishvakalhara.handymanbackend.services.NotificationService;
import io.github.vishvakalhara.handymanbackend.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final CategoryRepo categoryRepo;

    private final UserRepo userRepo;

    private final TaskRepo taskRepo;

    private final NotificationService notificationService;

    private final BidRepo bidRepo;

    private final S3Service s3Service;

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

        String imageUri = s3Service.uploadFile(data.getImage(), ResourceType.TASK_IMAGE);

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

        if (task.getIsDeleted()) {
            throw new AppException("Task is Not Found!", HttpStatus.NOT_FOUND);
        }

        return task;
    }

    @Override
    public List<Task> getMyTasks(UUID userId, Pageable pageable) {

        if (!userRepo.existsUserById(userId)) {
            throw new AppException("User not found!", HttpStatus.NOT_FOUND);
        }

        return taskRepo.findTasksByCreator_Id(userId, pageable);
    }

    @Transactional
    @Override
    public Task completeTask(UUID taskId, UUID creatorId) {

        Task task = taskRepo.findById(taskId).orElseThrow(
                () -> new AppException("Task not found!", HttpStatus.NOT_FOUND)
        );

        if (task.getIsDeleted()) {
            throw new AppException("Task not found!", HttpStatus.NOT_FOUND);
        }

        if (!userRepo.existsUserById(creatorId)) {
            throw new AppException("User not found!", HttpStatus.NOT_FOUND);
        }

        if (!task.getCreator().getId().equals(creatorId)) {
            throw new AppException("Only task owner can perform this action!", HttpStatus.FORBIDDEN);
        }

        task.setTaskStatus(TaskStatus.COMPLETED);

        List<Notification> notifications = new ArrayList<>();

        User acceptedBidder = bidRepo.findBidByAssociatedTask_IdAndBidStatus(
                task.getId(),
                BidStatus.ACCEPTED
        ).getBidder();

        // Notification for Handyman
        notifications.add(Notification.builder()
                .title("Action Required!")
                .message("Review your handyman!")
                .href("/tasks/" + task.getId())
                .associatedUser(task.getCreator())
                .hasNoted(true)
                .build()
        );

        // Notification for task owner
        notifications.add(Notification.builder()
                .title("Action Required!")
                .message("Review the task owner!")
                .href("/tasks/" + task.getId())
                .associatedUser(acceptedBidder)
                .build()
        );

        notificationService.AddNotification(notifications);

        return taskRepo.save(task);
    }

    @Override
    public void deleteTask(UUID id) {

        Task task = taskRepo.findById(id).orElseThrow(
                () -> new AppException("Task is Not Found!", HttpStatus.NOT_FOUND)
        );

        if (task.getIsDeleted()) {
            throw new AppException("Task is Not Found!", HttpStatus.NOT_FOUND);
        }

        task.setIsDeleted(true);
        taskRepo.save(task);
    }
}
