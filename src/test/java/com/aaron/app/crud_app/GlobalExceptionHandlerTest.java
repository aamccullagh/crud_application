package com.aaron.app.crud_app;

import com.aaron.app.crud_app.controller.APIController;
import com.aaron.app.crud_app.exception.GlobalExceptionHandler;
import com.aaron.app.crud_app.service.ApplicationServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({APIController.class, GlobalExceptionHandler.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApplicationServiceImplementation service;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testHandleTypeMismatchException() throws Exception {
        mockMvc.perform(put("/update/invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid type for parameter: id"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testHandleValidationExceptions() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "");  // Assuming name is mandatory
        requestBody.put("age", -1);  // Assuming age must be >= 0

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testHandleHttpMessageNotReadableException() throws Exception {
        String malformedJson = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"occupation\": \"Engineer\", }";

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Malformed JSON request"))
                .andExpect(jsonPath("$.message").exists());
    }
}