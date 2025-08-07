package com.nancologist.car360.integration_tests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nancologist.car360.dto.auth.LoginRequest;
import com.nancologist.car360.dto.auth.SignupRequest;
import com.nancologist.car360.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /signup - should respond 400 when password is smaller than 6 characters")
    public void testInvalidPassword() throws Exception {

        SignupRequest request = new SignupRequest();
        request.setUsername("john_doe");
        request.setEmail("john@example.com");
        request.setPassword("123");

        mockMvc.perform(
                post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.password")
                                .value("Password should be at least 6 characters long")
                );
    }

    @Test
    @DisplayName("POST /signup - should respond 400 when email is invalid")
    public void testInvalidEmail() throws Exception {

        SignupRequest request = new SignupRequest();
        request.setUsername("john_doe");
        request.setEmail("john@example");
        request.setPassword("1234567");

        mockMvc.perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.email")
                                .value("Email should be valid")
                );
    }

    @Test
    @DisplayName("POST /signup - should respond 400 when username is empty")
    public void testEmptyUsername() throws Exception {

        SignupRequest request = new SignupRequest();
        request.setUsername("");
        request.setEmail("john@example.com");
        request.setPassword("1234567");

        mockMvc.perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.username")
                                .value("must not be blank")
                );
    }

    @Test
    @DisplayName("POST /login - should successfully sign in the user")
    public void testSuccessfulLogin() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("john_69");
        request.setEmail("john@example.com");
        request.setPassword("1234567");

        mockMvc.perform(
                post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());

        System.out.println(userRepository.findAll());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("john_69");
        loginRequest.setPassword("1234567");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("POST /login - should fail to sign in the user, if password is wrong")
    public void testFailedLoginWithWrongPassword() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("john_69");
        request.setEmail("john@example.com");
        request.setPassword("1234567");

        mockMvc.perform(
                post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());

        System.out.println(userRepository.findAll());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("john_69");
        loginRequest.setPassword("abcdefghi");

        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid username or password"));
    }
}
