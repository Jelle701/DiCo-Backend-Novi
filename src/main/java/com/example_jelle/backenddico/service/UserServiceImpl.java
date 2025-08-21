package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.exception.EmailAlreadyExists;
import com.example_jelle.backenddico.exception.InvalidTokenException;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.dto.UserDto;
import com.example_jelle.backenddico.payload.request.VerifyRequest;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.AccessCodeRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final AccessCodeRepository accessCodeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccessCodeRepository accessCodeRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accessCodeRepository = accessCodeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) {
        String lowerCaseEmail = registerRequest.getEmail().toLowerCase();

        userRepository.findByEmail(lowerCaseEmail).ifPresent(u -> {
            throw new EmailAlreadyExists("This email address is already in use.");
        });

        User user = new User();
        user.setEmail(lowerCaseEmail);
        user.setUsername(lowerCaseEmail);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.PATIENT); // Default role, will be updated during onboarding
        user.setEnabled(false);

        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpires(LocalDateTime.now().plusHours(24));

        logger.info("Generated verification code for {}: {}", user.getEmail(), verificationCode);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void verifyUser(VerifyRequest verifyRequest) {
        String token = verifyRequest.getToken();
        logger.info("Attempting to verify user with token: '{}'", token);

        User user = userRepository.findByVerificationCode(token)
                .orElseThrow(() -> new InvalidTokenException(String.format("Invalid verification code: '%s'", token)));

        if (user.getVerificationCodeExpires().isBefore(LocalDateTime.now())) {
            logger.warn("Verification code '{}' for user {} has expired.", token, user.getEmail());
            throw new InvalidTokenException("Verification code has expired.");
        }

        logger.info("Successfully verified user {} with token '{}'", user.getEmail(), token);
        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpires(null);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveProfileDetails(String username, OnboardingRequestDto onboardingRequestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        // Validate and set the role
        try {
            Role role = Role.valueOf(onboardingRequestDto.getRole().toUpperCase());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid role specified: " + onboardingRequestDto.getRole());
        }

        // Set other profile details
        user.setDob(onboardingRequestDto.getDateOfBirth());
        // Note: Height, weight, etc. should ideally be in a separate UserProfile entity
        // For now, we assume they are directly on the User entity for simplicity, if not, this needs adjustment.

        userRepository.save(user);
        logger.info("Saved profile details for user: {}", username);
    }

    @Override
    public FullUserProfileDto getFullUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        FullUserProfileDto dto = new FullUserProfileDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(Collections.singleton(user.getRole().name()));
        return dto;
    }

    // ... other methods ...
    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(this::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public String generateAccessCode(String patientEmail) {
        User user = userRepository.findByUsername(patientEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + patientEmail));

        String code = String.format("%s-%s-%s",
            UUID.randomUUID().toString().substring(0, 3).toUpperCase(),
            UUID.randomUUID().toString().substring(0, 3).toUpperCase(),
            UUID.randomUUID().toString().substring(0, 3).toUpperCase());

        AccessCode accessCode = new AccessCode();
        accessCode.setUser(user);
        accessCode.setCode(code);
        accessCode.setCreationTime(LocalDateTime.now());
        accessCode.setExpirationTime(LocalDateTime.now().plusHours(24));

        accessCodeRepository.save(accessCode);
        return code;
    }

    @Override
    public String getAccessCode(String patientEmail) {
        User user = userRepository.findByUsername(patientEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + patientEmail));

        return accessCodeRepository.findByUserIdAndExpirationTimeAfter(user.getId(), LocalDateTime.now())
                .map(AccessCode::getCode)
                .orElse(null);
    }

    private UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }
}
