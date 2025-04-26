package io.github.vishvakalhara.handymanbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishvakalhara.handymanbackend.domains.TaskStatus;
import io.github.vishvakalhara.handymanbackend.domains.dtos.bids.CreateBidRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.Task;
import io.github.vishvakalhara.handymanbackend.repositories.BidRepo;
import io.github.vishvakalhara.handymanbackend.repositories.CategoryRepo;
import io.github.vishvakalhara.handymanbackend.repositories.TaskRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static io.github.vishvakalhara.handymanbackend.operations.TaskOperations.createMockTask;
import static io.github.vishvakalhara.handymanbackend.operations.CategoryOperations.createMockCategory;
import static io.github.vishvakalhara.handymanbackend.operations.UserOperations.createUserAndGetToken;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private TaskRepo taskRepo;

    private String authToken;

    private UUID mockTaskId;

    private CreateBidRequest reqBody;

    @BeforeAll
    public void doBeforeAll() throws Exception {

        this.authToken = createUserAndGetToken(mockMvc, objectMapper);
        this.mockTaskId = createMockTask(mockMvc, objectMapper, createMockCategory(categoryRepo), this.authToken);
    }

    @BeforeEach
    public void doBeforeEach() {
        this.reqBody = CreateBidRequest.builder()
                .taskId(this.mockTaskId)
                .price(5000D)
                .build();
    }

    @AfterEach
    public void doAfterEach() {
        bidRepo.deleteAll();
    }

    @Test
    public void testCreateBid_EndpointMustBeSecured() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/bids")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testCreateBid_PriceNotPresent() throws Exception {

        this.reqBody.setPrice(0D);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/bids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.reqBody))
                                .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBid_PriceIsNegative() throws Exception {

        this.reqBody.setPrice(-1000D);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/bids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.reqBody))
                                .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBid_TaskIdNotPresent() throws Exception {

        this.reqBody.setTaskId(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/bids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.reqBody))
                                .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBid_TaskIsNotFound() throws Exception {

        this.reqBody.setTaskId(UUID.randomUUID());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/bids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.reqBody))
                                .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Task Not Found!"));
    }

    @Test
    public void testCreateBid_TaskIsDeleted() throws Exception {

        UUID taskToBeDeletedId = createMockTask(mockMvc, objectMapper, categoryRepo, authToken);
        Task taskToBeDeleted = taskRepo.findById(taskToBeDeletedId).get();
        taskToBeDeleted.setIsDeleted(true);
        taskRepo.save(taskToBeDeleted);

        this.reqBody.setTaskId(taskToBeDeletedId);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/bids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.reqBody))
                                .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Task Not Found!"));
    }

    @Test
    public void testCreateBid_TaskStatusIsNotPending() throws Exception {

        UUID taskToBeUpdatedId = createMockTask(mockMvc, objectMapper, categoryRepo, authToken);
        Task taskToBeUpdated = taskRepo.findById(taskToBeUpdatedId).get();
        taskToBeUpdated.setTaskStatus(TaskStatus.WAITING_TO_COMPLETE);
        taskRepo.save(taskToBeUpdated);

        this.reqBody.setTaskId(taskToBeUpdatedId);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/bids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.reqBody))
                                .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Task does not accept bids anymore!"));
    }

    @Test
    public void testCreateBid_TaskOwnerCannotBid() throws Exception {

        this.reqBody.setTaskId(this.mockTaskId);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.reqBody))
                        .header("Authorization", "Bearer " + authToken)
        ).andExpect(status().isForbidden());
    }
}
