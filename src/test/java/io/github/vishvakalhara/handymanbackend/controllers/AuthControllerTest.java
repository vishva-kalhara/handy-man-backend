package io.github.vishvakalhara.handymanbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.LoginRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.RegisterRequest;
import io.github.vishvakalhara.handymanbackend.repositories.NotificationRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
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

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    private final RegisterRequest registerRequestBody;

    private final LoginRequest loginRequestBody;

    public AuthControllerTest() {

        String validUserEmail = "test@example.com";
        String validUserPassword = "123456";

        this.registerRequestBody = new RegisterRequest(
                "Test Display Name",
                validUserEmail,
                validUserPassword
        );
        this.loginRequestBody = new LoginRequest(validUserEmail, validUserPassword);
    }

    @AfterEach
    public void doAfterEach() {
        notificationRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void testRegister_EmptyRequestBody() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new RegisterRequest()))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testRegister_ValidUser() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.registerRequestBody))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(Matchers.notNullValue()));
    }

    @Test
    public void testRegister_SameUserEmail() throws Exception {

        testRegister_ValidUser();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(this.registerRequestBody))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Matchers.startsWith("Already there is a user with the same email")));
    }

    @Test
    public void testLogin_ValidCredentials() throws Exception {

        testRegister_ValidUser();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.loginRequestBody))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {

        testRegister_ValidUser();

        this.loginRequestBody.setPassword("Wrong Password");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.loginRequestBody))
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
