package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.AdminUpdateUserDto;
import com.example_jelle.backenddico.dto.AdminUserDto;
import com.example_jelle.backenddico.dto.onboarding.DeviceDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.UserDto;
import com.example_jelle.backenddico.exception.BadRequestException;
import com.example_jelle.backenddico.exception.EmailAlreadyExists;
import com.example_jelle.backenddico.exception.InvalidTokenException;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.*;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.payload.request.VerifyRequest;
import com.example_jelle.backenddico.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final AccessCodeRepository accessCodeRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, AccessCodeRepository accessCodeRepository, UserDeviceRepository userDeviceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.accessCodeRepository = accessCodeRepository;
        this.userDeviceRepository = userDeviceRepository;
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
        user.setRole(Role.PATIENT);
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
        User user = userRepository.findByVerificationCode(token)
                .orElseThrow(() -> new InvalidTokenException(String.format("Invalid verification code: '%s'", token)));
        if (user.getVerificationCodeExpires().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Verification code has expired.");
        }
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

        UserProfile userProfile = user.getUserProfile();
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUser(user);
            user.setUserProfile(userProfile);
        }

        try {
            Role role = Role.valueOf(onboardingRequestDto.getRole().toUpperCase());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role specified: " + onboardingRequestDto.getRole());
        }

        user.setFirstName(onboardingRequestDto.getFirstName());
        user.setLastName(onboardingRequestDto.getLastName());
        user.setDob(onboardingRequestDto.getDateOfBirth());
        userProfile.setLength(onboardingRequestDto.getHeight());
        userProfile.setWeight(onboardingRequestDto.getWeight());
        userProfile.setGender(onboardingRequestDto.getGender());
        userProfile.setDiabetesType(onboardingRequestDto.getDiabetesType());
        userProfile.setLongActingInsulin(onboardingRequestDto.getLongActingInsulin());
        userProfile.setShortActingInsulin(onboardingRequestDto.getShortActingInsulin());

        if (onboardingRequestDto.getDiabeticDevices() != null && !onboardingRequestDto.getDiabeticDevices().isEmpty()) {
            logger.info("Received diabetic devices for user {}: {}", username, onboardingRequestDto.getDiabeticDevices());
        } else {
            logger.info("No diabetic devices received for user {}.", username);
        }

        logger.info("User devices BEFORE clearing for user {}: {}", username, user.getUserDevices());
        user.getUserDevices().clear();
        logger.info("User devices AFTER clearing for user {}: {}", username, user.getUserDevices());

        if (onboardingRequestDto.getDiabeticDevices() != null) {
            for (DeviceDto deviceDto : onboardingRequestDto.getDiabeticDevices()) {
                UserDevice device = new UserDevice();
                device.setCategory(deviceDto.getCategory().name());
                device.setManufacturer(deviceDto.getBrand());
                device.setModel(deviceDto.getModel());
                user.getUserDevices().add(device);
                device.setUser(user);
            }
        }
        logger.info("User devices AFTER adding new devices for user {}: {}", username, user.getUserDevices());

        boolean hasAllPatientDetails = false;
        if (user.getRole() == Role.PATIENT) {
            if (onboardingRequestDto.getFirstName() != null && !onboardingRequestDto.getFirstName().trim().isEmpty() &&
                onboardingRequestDto.getLastName() != null && !onboardingRequestDto.getLastName().trim().isEmpty() &&
                onboardingRequestDto.getDateOfBirth() != null &&
                onboardingRequestDto.getGender() != null &&
                onboardingRequestDto.getHeight() > 0 &&
                onboardingRequestDto.getWeight() > 0 &&
                onboardingRequestDto.getDiabetesType() != null &&
                onboardingRequestDto.getLongActingInsulin() != null && !onboardingRequestDto.getLongActingInsulin().trim().isEmpty() &&
                onboardingRequestDto.getShortActingInsulin() != null && !onboardingRequestDto.getShortActingInsulin().trim().isEmpty() &&
                onboardingRequestDto.getDiabeticDevices() != null) {
                hasAllPatientDetails = true;
            }
            user.getFlags().setHasDetails(hasAllPatientDetails);
        } else {
            user.getFlags().setHasDetails(true);
        }

        userRepository.save(user);
        logger.info("Saved profile details and devices for user: {}", username);
        logger.info("User devices AFTER saving user entity for user {}: {}", username, user.getUserDevices());
    }

    @Override
    @Transactional(readOnly = true)
    public FullUserProfileDto getFullUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return new FullUserProfileDto(user);
    }

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

        // Invalidate all existing active codes for this user
        List<AccessCode> activeCodes = accessCodeRepository.findAllByUserAndExpirationTimeAfter(user, LocalDateTime.now());
        if (!activeCodes.isEmpty()) {
            accessCodeRepository.deleteAll(activeCodes);
        }

        String code = String.format("%06d", new Random().nextInt(1000000));

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

        List<AccessCode> activeCodes = accessCodeRepository.findAllByUserAndExpirationTimeAfter(user, LocalDateTime.now());
        if (activeCodes.isEmpty()) {
            return null;
        }
        // Return the first active code found
        return activeCodes.get(0).getCode();
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserDto> getAllUsersForAdmin() {
        return userRepository.findAll().stream()
                .map(AdminUserDto::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (user.getRole() == Role.ADMIN) {
            throw new BadRequestException("Admins cannot be deleted via this endpoint.");
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public AdminUserDto updateUserAsAdmin(Long userId, AdminUpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Prevent changing an admin's role
        if (user.getRole() == Role.ADMIN && StringUtils.hasText(updateUserDto.getRole()) && !user.getRole().name().equals(updateUserDto.getRole().toUpperCase())) {
            throw new BadRequestException("The role of an admin cannot be changed.");
        }

        if (StringUtils.hasText(updateUserDto.getFirstName())) {
            user.setFirstName(updateUserDto.getFirstName());
        }

        if (StringUtils.hasText(updateUserDto.getLastName())) {
            user.setLastName(updateUserDto.getLastName());
        }

        if (StringUtils.hasText(updateUserDto.getRole())) {
            try {
                Role newRole = Role.valueOf(updateUserDto.getRole().toUpperCase());
                user.setRole(newRole);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid role specified: " + updateUserDto.getRole());
            }
        }

        User updatedUser = userRepository.save(user);
        return AdminUserDto.fromUser(updatedUser);
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
