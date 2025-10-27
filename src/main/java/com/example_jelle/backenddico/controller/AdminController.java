package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.AdminUpdateUserDto;
import com.example_jelle.backenddico.dto.AdminUserDto;
import com.example_jelle.backenddico.services.GlucoseDataService;
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

    public AdminController(UserService userService, GlucoseDataService glucoseDataService) {
        this.userService = userService;
        this.glucoseDataService = glucoseDataService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        List<AdminUserDto> users = userService.getAllUsersForAdmin();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(Map.of("message", "User with ID " + userId + " deleted successfully."));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<AdminUserDto> updateUser(@PathVariable Long userId, @RequestBody AdminUpdateUserDto updateUserDto) {
        AdminUserDto updatedUser = userService.updateUserAsAdmin(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Endpoint to delete all glucose data for a specific user.
     * This is a destructive operation and is restricted to ADMIN users.
     *
     * @param userId The ID of the user whose glucose data should be deleted.
     * @return A response entity indicating the result of the operation.
     */
    @DeleteMapping("/users/{userId}/glucose-data")
    @PreAuthorize("hasRole('ADMIN')") // Beveilig het endpoint!
    public ResponseEntity<?> deleteGlucoseDataForUser(@PathVariable Long userId) {
        try {
            glucoseDataService.deleteDataForUser(userId);
            logger.info("Admin action: Glucose data for user with ID {} has been deleted.", userId);
            return ResponseEntity.ok().body("Glucose data voor de gebruiker is succesvol verwijderd.");
        } catch (Exception e) {
            logger.error("Admin action: Failed to delete glucose data for user with ID {}.", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Er is een fout opgetreden bij het verwijderen van de glucose data.");
        }
    }
}
