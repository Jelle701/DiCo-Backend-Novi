package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.config.SecurityConfig;
import com.example_jelle.backenddico.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@Import(SecurityConfig.class) // Import the security configuration
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock the beans that SecurityConfig depends on
    @MockBean
    private UserService userService;

    @MockBean
    private com.example_jelle.backenddico.security.JwtUtil jwtUtil;

    @MockBean
    private com.example_jelle.backenddico.exception.CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockBean
    private com.example_jelle.backenddico.service.CustomUserDetailsService customUserDetailsService;


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_whenAdmin_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllUsers_whenUser_shouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }
}
