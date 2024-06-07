package com.aaron.app.crud_app;

import com.aaron.app.crud_app.controller.APIController;
import com.aaron.app.crud_app.models.User;
import com.aaron.app.crud_app.service.ApplicationServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class APIControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationServiceImplementation service;

    @InjectMocks
    private APIController apiController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "John", "Doe", "Engineer"));
        userList.add(new User(2, "Jane", "Doe", "Doctor"));

        when(service.getUsers()).thenReturn(userList);

        mockMvc.perform(get("/read"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(service, times(1)).getUsers();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User(1, "John", "Doe", "Engineer");

        when(service.addUser(any(User.class))).thenReturn("User added successfully");

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"occupation\":\"Engineer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));

        verify(service, times(1)).addUser(any(User.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User(1, "John", "Doe", "Engineer");

        when(service.updateUser(eq(1), any(User.class))).thenReturn("User updated successfully");

        mockMvc.perform(put("/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"occupation\":\"Engineer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully"));

        verify(service, times(1)).updateUser(eq(1), any(User.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(service.deleteUser(1)).thenReturn("User deleted successfully");

        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));

        verify(service, times(1)).deleteUser(1);
        verifyNoMoreInteractions(service);
    }
}