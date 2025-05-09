package io.github.vishvakalhara.handymanbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.LoginRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.RegisterRequest;
import io.github.vishvakalhara.handymanbackend.repositories.NotificationRepo;
import io.github.vishvakalhara.handymanbackend.repositories.UserRepo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

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

    @BeforeEach
    public void doBeforeEach() {
        RestAssured.port = port;
    }

    @AfterEach
    public void doAfterEach() {
        notificationRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void testRegister_EmptyRequestBody() throws Exception {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new RegisterRequest()))
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(400)
        ;
    }

    @Test
    public void testRegister_ValidUser() throws Exception {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(this.registerRequestBody))
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void testRegister_SameUserEmail() throws Exception {

        testRegister_ValidUser();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(this.registerRequestBody))
                .when()
                .post("/api/v1/auth/register")
                .then()
                .statusCode(400)
                .body("message", startsWith("Already there is a user with the same email"));
    }

    @Test
    public void testLogin_ValidCredentials() throws Exception {

        testRegister_ValidUser();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(this.loginRequestBody))
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {

        testRegister_ValidUser();

        this.loginRequestBody.setPassword("Wrong Password");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(this.loginRequestBody))
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(401);
    }
}
