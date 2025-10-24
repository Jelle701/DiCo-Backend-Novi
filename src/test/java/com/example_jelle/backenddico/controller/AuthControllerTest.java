package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.config.SecurityConfig;
import com.example_jelle.backenddico.exception.CustomAccessDeniedHandler;
import com.example_jelle.backenddico.payload.request.AuthenticationRequest;
import com.example_jelle.backenddico.security.JwtUtil;
import com.example_jelle.backenddico.service.CustomUserDetailsService;
import com.example_jelle.backenddico.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class) // Import the actual security configuration
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    // Mock all beans that SecurityConfig depends on
    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Test
    void createAuthenticationToken_withValidCredentials_shouldReturnJwt() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("user@example.com");
        request.setPassword("password");

        // Since the endpoint is now permitAll, we don't need to mock the full authentication flow,
        // but we still need to mock the service calls made by the controller method itself.
        UserDetails userDetails = new User("user@example.com", "password", new ArrayList<>());
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("mock.jwt.token");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("mock.jwt.token"));
    }
}
