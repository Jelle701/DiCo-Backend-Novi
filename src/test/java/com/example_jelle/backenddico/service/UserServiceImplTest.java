package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.AdminUpdateUserDto;
import com.example_jelle.backenddico.exception.BadRequestException;
import com.example_jelle.backenddico.exception.EmailAlreadyExists;
import com.example_jelle.backenddico.exception.InvalidTokenException;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.enums.ActivityType;
import com.example_jelle.backenddico.model.enums.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.payload.request.VerifyRequest;
import com.example_jelle.backenddico.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest registerRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setRole(Role.PATIENT);
    }

    // 8 tests from before ...
    @Test
    void register_shouldSaveNewUser_withEncodedPassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        userService.register(registerRequest);

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(registerRequest.getEmail(), savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(activityService).createActivity(eq(ActivityType.USER_REGISTRATION), anyString());
    }

    @Test
    void register_shouldThrowException_whenEmailExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        assertThrows(EmailAlreadyExists.class, () -> userService.register(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void verifyUser_shouldEnableUser_whenTokenIsValid() {
        VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setToken("valid-token");
        testUser.setVerificationCode("valid-token");
        testUser.setVerificationCodeExpires(LocalDateTime.now().plusHours(1));
        when(userRepository.findByVerificationCode("valid-token")).thenReturn(Optional.of(testUser));

        userService.verifyUser(verifyRequest);

        assertTrue(testUser.isEnabled());
        assertNull(testUser.getVerificationCode());
        verify(userRepository).save(testUser);
    }

    @Test
    void verifyUser_shouldThrowException_whenTokenIsInvalid() {
        VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setToken("invalid-token");
        when(userRepository.findByVerificationCode("invalid-token")).thenReturn(Optional.empty());
        assertThrows(InvalidTokenException.class, () -> userService.verifyUser(verifyRequest));
    }

    @Test
    void verifyUser_shouldThrowException_whenTokenIsExpired() {
        VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setToken("expired-token");
        testUser.setVerificationCode("expired-token");
        testUser.setVerificationCodeExpires(LocalDateTime.now().minusHours(1));
        when(userRepository.findByVerificationCode("expired-token")).thenReturn(Optional.of(testUser));
        assertThrows(InvalidTokenException.class, () -> userService.verifyUser(verifyRequest));
    }

    @Test
    void deleteUserById_shouldDeleteUser_whenNotAdmin() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        userService.deleteUserById(1L);
        verify(userRepository).delete(testUser);
        verify(activityService).createActivity(eq(ActivityType.USER_DELETION), anyString());
    }

    @Test
    void deleteUserById_shouldThrowException_whenUserIsAdmin() {
        testUser.setRole(Role.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        assertThrows(BadRequestException.class, () -> userService.deleteUserById(1L));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void deleteUserById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(99L));
    }

    // New tests for updateUserAsAdmin
    @Test
    void updateUserAsAdmin_shouldUpdateFields_whenUserExists() {
        // Arrange
        AdminUpdateUserDto updateUserDto = new AdminUpdateUserDto();
        updateUserDto.setFirstName("Jelle");
        updateUserDto.setLastName("De Jager");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.updateUserAsAdmin(1L, updateUserDto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("Jelle", userCaptor.getValue().getFirstName());
        assertEquals("De Jager", userCaptor.getValue().getLastName());
    }

    @Test
    void updateUserAsAdmin_shouldChangeRole_andCreateActivity() {
        // Arrange
        AdminUpdateUserDto updateUserDto = new AdminUpdateUserDto();
        updateUserDto.setRole("PROVIDER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.updateUserAsAdmin(1L, updateUserDto);

        // Assert
        assertEquals(Role.PROVIDER, testUser.getRole());
        verify(activityService).createActivity(eq(ActivityType.ROLE_CHANGE), anyString());
    }

    @Test
    void updateUserAsAdmin_shouldThrowException_whenChangingAdminRole() {
        // Arrange
        testUser.setRole(Role.ADMIN);
        AdminUpdateUserDto updateUserDto = new AdminUpdateUserDto();
        updateUserDto.setRole("PATIENT");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            userService.updateUserAsAdmin(1L, updateUserDto);
        });
    }

    @Test
    void updateUserAsAdmin_shouldThrowException_whenRoleIsInvalid() {
        // Arrange
        AdminUpdateUserDto updateUserDto = new AdminUpdateUserDto();
        updateUserDto.setRole("INVALID_ROLE");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            userService.updateUserAsAdmin(1L, updateUserDto);
        });
    }
}
