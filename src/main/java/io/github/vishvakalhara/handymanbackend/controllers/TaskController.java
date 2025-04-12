package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.TaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.UpdateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.mappers.TaskMapper;
import io.github.vishvakalhara.handymanbackend.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {

//        Path Variables: isDeleted, isCompleted, etc.

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getOneTask(@PathVariable UUID id) {

        Task task = taskService.getOneTask(id);
        return ResponseEntity.ok(taskMapper.entityToDTO(task));
    }

    @PostMapping(consumes =  {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
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

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> updateOneTask(@PathVariable UUID id, @RequestBody UpdateTaskRequest requestBody) {

//        When owner mark the task as completed must create two dummy review spots automatically.


        return ResponseEntity.ok(null);
    }
}