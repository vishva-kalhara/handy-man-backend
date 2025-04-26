package io.github.vishvakalhara.handymanbackend.operations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishvakalhara.handymanbackend.domains.dtos.tasks.CreateTaskRequest;
import io.github.vishvakalhara.handymanbackend.repositories.CategoryRepo;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static io.github.vishvakalhara.handymanbackend.operations.CategoryOperations.createMockCategory;

public class TaskOperations {

    public static UUID createMockTask(MockMvc mockMvc, ObjectMapper objectMapper, CategoryRepo categoryRepo, String authToken) throws Exception {

        UUID categoryId = createMockCategory(categoryRepo);
        return createMockTask(mockMvc, objectMapper, categoryId, authToken);
    }

    public static synchronized UUID createMockTask(MockMvc mockMvc, ObjectMapper objectMapper, UUID categoryId, String authToken) throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "dummy-image-content".getBytes()
        );

        CreateTaskRequest requestData = CreateTaskRequest.builder()
                .title("Sample Task")
                .description("Do something")
                .maxPrice(100D)
                .categoryId(categoryId)
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
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = res.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return UUID.fromString(jsonNode.get("id").asText());
    }
}
