package com.aaron.app.crud_app;

import com.aaron.app.crud_app.service.ApplicationServiceImplementation;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationServiceImplementation service;


    @MockBean
    private InMemoryUserDetailsManager userDetailsService;

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void whenAuthenticated_thenAllowAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/read"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenNotAuthenticated_thenDenyAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/read"))
                .andExpect(status().isUnauthorized());
    }
}