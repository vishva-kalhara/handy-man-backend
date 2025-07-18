package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.*;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.mappers.TaskMapper;
import io.github.vishvakalhara.handymanbackend.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<SimpleTaskDTO>> getAllTasks(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean isEmergency,
            @RequestParam(required = false) UUID creatorId,
            @RequestParam(required = false) TaskStatus taskStatus,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        FilterTasksRequest queryData = FilterTasksRequest.builder()
                .category(category)
                .isEmergency(isEmergency)
                .creatorId(creatorId)
                .taskStatus(taskStatus)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .isDeleted(isDeleted)
                .size(size == null ? 24 : size)
                .page(page == null ? 0 : page - 1) // First Page
                .sort(sort)
                .build();

        List<Task> tasks = taskService.getAllTasks(queryData);
        return ResponseEntity.ok(taskMapper.entityToSimpleTaskDTO(tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getOneTask(@PathVariable UUID id) {

        Task task = taskService.getOneTask(id);
        return ResponseEntity.ok(taskMapper.entityToDTO(task));
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<List<SimpleTaskDTO>> getMyTasks(
            @RequestAttribute UUID userId,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        List<Task> tasks = taskService.getMyTasks(userId, pageable);

        return ResponseEntity.ok(taskMapper.entityToSimpleTaskDTO(tasks));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<TaskDTO> uploadFile(
            @RequestPart("image") MultipartFile file,
            @RequestPart("data") CreateTaskRequest data,
            @RequestAttribute UUID userId
    ) {

        CreateTaskRequest requestData = CreateTaskRequest.builder()
                .image(file)
                .title(data.getTitle())
                .description(data.getDescription())
                .maxPrice(data.getMaxPrice())
                .categoryId(data.getCategoryId())
                .isEmergency(data.getIsEmergency())
                .creatorId(userId)
                .build();

        Task task = taskService.createTask(requestData);
        return new ResponseEntity<>(taskMapper.entityToDTO(task), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneTask(@PathVariable UUID id) {

        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<SimpleTaskDTO> completeTask(
            @PathVariable UUID taskId,
            @RequestAttribute UUID userId
    ) {

        Task updatedTask = taskService.completeTask(taskId, userId);
        return ResponseEntity.ok(taskMapper.entityToSimpleTaskDTO(updatedTask));
    }
}