package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.AdminUpdateUserDto;
import com.example_jelle.backenddico.dto.AdminUserDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of all users for the admin dashboard.
     * @return A ResponseEntity containing a list of AdminUserDto objects.
     */
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        List<AdminUserDto> users = userService.getAllUsersForAdmin();
        return ResponseEntity.ok(users);
    }

    /**
     * Deletes a user by their ID.
     * @param userId The ID of the user to delete.
     * @return A ResponseEntity with a confirmation message.
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(Map.of("message", "User with ID " + userId + " deleted successfully."));
    }

    /**
     * Updates a user's details.
     * @param userId The ID of the user to update.
     * @param updateUserDto The DTO containing the fields to update.
     * @return A ResponseEntity with the updated user details.
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<AdminUserDto> updateUser(@PathVariable Long userId, @RequestBody AdminUpdateUserDto updateUserDto) {
        AdminUserDto updatedUser = userService.updateUserAsAdmin(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
}
