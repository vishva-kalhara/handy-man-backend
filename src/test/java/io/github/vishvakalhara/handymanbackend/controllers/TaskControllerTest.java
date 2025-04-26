package io.github.vishvakalhara.handymanbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.repositories.CategoryRepo;
import io.github.vishvakalhara.handymanbackend.repositories.NotificationRepo;
import io.github.vishvakalhara.handymanbackend.repositories.TaskRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static io.github.vishvakalhara.handymanbackend.operations.TaskOperations.createMockTask;
import static io.github.vishvakalhara.handymanbackend.operations.UserOperations.createUserAndGetToken;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    private String authToken;

    @BeforeAll
    public void doBeforeAll() throws Exception {

        this.authToken = createUserAndGetToken(mockMvc, objectMapper);
    }

    @AfterAll
    public void doAfterAll() {
        taskRepo.deleteAll();
        categoryRepo.deleteAll();
        notificationRepo.deleteAll();
        userRepo.deleteAll();
    }

    @AfterEach
    public void doAfterEach() {
        taskRepo.deleteAll();
    }

    @Test
    public void testCreateTask_EndpointMustBeSecured() throws Exception {

        mockMvc.perform(multipart("/api/v1/tasks")
                        .requestAttr("userId", UUID.randomUUID())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateTask_ValidTask() throws Exception {

        createMockTask(mockMvc, objectMapper, categoryRepo, authToken);
    }

    @Test
    public void testDeleteTask_EndpointMustBeSecured() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/tasks/123456")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteTask_InvalidTaskId() throws Exception {

        testCreateTask_ValidTask();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/tasks/" + UUID.randomUUID())
                        .header("Authorization", "Bearer " + this.authToken)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask_TaskIsAlreadyDeleted() throws Exception {

        UUID taskId = createMockTask(mockMvc, objectMapper, categoryRepo, authToken);

        Task task = taskRepo.findById(taskId).get();
        task.setIsDeleted(true);
        taskRepo.save(task);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/tasks/" + taskId)
                        .header("Authorization", "Bearer " + this.authToken)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask_TaskToBeDeleted() throws Exception {

        UUID taskId = createMockTask(mockMvc, objectMapper, categoryRepo, authToken);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/tasks/" + taskId)
                        .header("Authorization", "Bearer " + this.authToken)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void testCompleteTask_EndpointMustBeSecured() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/tasks/" + UUID.randomUUID() + "/complete")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testCompleteTask_InvalidTaskId() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/tasks/" + UUID.randomUUID() + "/complete")
                        .header("Authorization", "Bearer " + this.authToken)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testCompleteTask_TaskIsDeleted() throws Exception {

        UUID taskId = createMockTask(mockMvc, objectMapper, categoryRepo, authToken);

        Task task = taskRepo.findById(taskId).get();
        task.setIsDeleted(true);
        taskRepo.save(task);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/tasks/" + UUID.randomUUID() + "/complete")
                        .header("Authorization", "Bearer " + this.authToken)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testOneTasks_InvalidTaskId() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/tasks/" + UUID.randomUUID())
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testOneTasks_ValidTaskId() throws Exception {

        UUID taskId = createMockTask(mockMvc, objectMapper, categoryRepo, authToken);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/tasks/" + taskId)
        ).andExpect(status().isOk());
    }
}
