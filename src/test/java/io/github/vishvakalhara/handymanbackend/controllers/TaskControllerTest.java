package io.github.vishvakalhara.handymanbackend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.RegisterRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private static final String VALID_USERNAME = "test@example.com";

    private String validTaskId;

    private UUID validCategoryId;

    private String authToken;

    @BeforeAll
    public void doBeforeAll() throws Exception {

        this.validCategoryId = categoryRepo.save(
                Category.builder()
                        .categoryName("Test Category")
                        .build()
        ).getId();

        MvcResult res = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest(
                                        "Test Display Name",
                                        VALID_USERNAME,
                                        "123456"
                                ))
                        )
        ).andReturn();

        String responseBody = res.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        this.authToken = jsonNode.get("token").asText();
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

        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "dummy-image-content".getBytes()
        );

        CreateTaskRequest requestData = CreateTaskRequest.builder()
                .title("Sample Task")
                .description("Do something")
                .maxPrice(100D)
                .categoryId(this.validCategoryId)
                .isEmergency(false)
                .build();

        MockMultipartFile data = new MockMultipartFile(
                "data", "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(requestData)
        );

        MvcResult res = mockMvc.perform(multipart("/api/v1/tasks")
                        .file(imageFile)
                        .file(data)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer " + this.authToken))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = res.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        this.validTaskId = jsonNode.get("id").asText();
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

        testCreateTask_ValidTask();

        Task task = taskRepo.findById(UUID.fromString(this.validTaskId)).get();
        task.setIsDeleted(true);
        taskRepo.save(task);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/tasks/" + this.validTaskId)
                        .header("Authorization", "Bearer " + this.authToken)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask_TaskToBeDeleted() throws Exception {

        testCreateTask_ValidTask();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/tasks/" + this.validTaskId)
                        .header("Authorization", "Bearer " + this.authToken)
        ).andExpect(status().isNoContent());
    }


}
