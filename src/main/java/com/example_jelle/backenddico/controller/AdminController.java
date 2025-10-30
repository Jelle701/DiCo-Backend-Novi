// REST controller for administrative operations.
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.AdminUpdateUserDto;
import com.example_jelle.backenddico.dto.AdminUserDto;
import com.example_jelle.backenddico.service.GlucoseDataService;
import com.example_jelle.backenddico.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;
    private final GlucoseDataService glucoseDataService;

    // Constructs a new AdminController.
    public AdminController(UserService userService, GlucoseDataService glucoseDataService) {
        this.userService = userService;
        this.glucoseDataService = glucoseDataService;
    }

    // Retrieves a list of all users.
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        List<AdminUserDto> users = userService.getAllUsersForAdmin();
        return ResponseEntity.ok(users);
    }

    // Deletes a user by their ID.
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(Map.of("message", "User with ID " + userId + " deleted successfully."));
    }

    // Updates a user's details.
    @PutMapping("/users/{userId}")
    public ResponseEntity<AdminUserDto> updateUser(@PathVariable Long userId, @RequestBody AdminUpdateUserDto updateUserDto) {
        AdminUserDto updatedUser = userService.updateUserAsAdmin(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Deletes all glucose data for a specific user.
    @DeleteMapping("/users/{userId}/glucose-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteGlucoseDataForUser(@PathVariable Long userId) {
        try {
            glucoseDataService.deleteDataForUser(userId);
            logger.info("Admin action: Glucose data for user with ID {} has been deleted.", userId);
            return ResponseEntity.ok().body("Glucose data for the user has been successfully deleted.");
        } catch (Exception e) {
            logger.error("Admin action: Failed to delete glucose data for user with ID {}.", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while deleting glucose data.");
        }
    }
}
