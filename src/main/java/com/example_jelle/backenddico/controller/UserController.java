package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller handles user-related operations, such as retrieving user profiles.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the full profile of a user by their username.
     * @param username The username of the user to retrieve.
     * @return A ResponseEntity containing the user's full profile.
     */
    @GetMapping("/{username}/profile")
    public ResponseEntity<FullUserProfileDto> getFullUserProfile(@PathVariable String username) {
        FullUserProfileDto userProfile = userService.getFullUserProfile(username);
        return ResponseEntity.ok(userProfile);
    }
}
