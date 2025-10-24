package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.access.GrantAccessResponseDto;
import com.example_jelle.backenddico.exception.InvalidAccessException;
import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.AccessCodeRepository;
import com.example_jelle.backenddico.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessServiceImplTest {

    @Mock
    private AccessCodeRepository accessCodeRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AccessServiceImpl accessService;

    private User patient;
    private AccessCode validAccessCode;

    @BeforeEach
    void setUp() {
        patient = new User();
        patient.setUsername("patient@example.com");

        validAccessCode = new AccessCode();
        validAccessCode.setCode("123456");
        validAccessCode.setUser(patient);
        validAccessCode.setExpirationTime(LocalDateTime.now().plusHours(1));
    }

    @Test
    void grantAccess_shouldReturnDelegatedToken_whenCodeIsValid() {
        // Arrange
        when(accessCodeRepository.findByCodeAndExpirationTimeAfter(eq("123456"), any(LocalDateTime.class)))
                .thenReturn(Optional.of(validAccessCode));
        when(jwtUtil.generateDelegatedToken("patient@example.com")).thenReturn("delegated.jwt.token");

        // Act
        GrantAccessResponseDto response = accessService.grantAccess("123456");

        // Assert
        assertNotNull(response);
        assertEquals("delegated.jwt.token", response.getDelegatedToken());
        assertEquals("patient@example.com", response.getPatientUsername());
    }

    @Test
    void grantAccess_shouldThrowException_whenCodeIsInvalidOrExpired() {
        // Arrange
        when(accessCodeRepository.findByCodeAndExpirationTimeAfter(anyString(), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        InvalidAccessException exception = assertThrows(InvalidAccessException.class, () -> {
            accessService.grantAccess("invalid-code");
        });

        assertEquals("Access code is invalid or expired.", exception.getMessage());
    }
}
