package io.github.vishvakalhara.handymanbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final RegisterRequest registerRequestBody;

    public AuthControllerTest(){
        this.registerRequestBody = new RegisterRequest(
                "Test Display Name",
                "test@example.com",
                "123456"
        );
    }

    @Test
    public void testRegister_EmptyRequestBody() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest()))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testRegister_ValidUser() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.registerRequestBody))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
