package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.TaskDTO;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.UpdateTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(){

//        Path Variables: isDeleted, isCompleted, etc.

        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getOneTask(@PathVariable UUID id){

        // Must check for isDeleted = false

        return ResponseEntity.ok(new TaskDTO());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<TaskDTO> uploadFile(
            @RequestPart("image") MultipartFile file,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("maxPrice") String maxPrice,
            @RequestPart("isEmergency") String isEmergency,
            @RequestPart("category") String category)  {

//        https://chatgpt.com/c/67ebb44a-7cec-8002-94dd-c78b3c5c10a3

        return new ResponseEntity<>(new TaskDTO(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneTask(@PathVariable UUID id){

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> updateOneTask(@PathVariable UUID id, @RequestBody UpdateTaskRequest requestBody){

//        When owner mark the task as completed must create two dummy review spots automatically.



        return ResponseEntity.ok(null);
    }
}