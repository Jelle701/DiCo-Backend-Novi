package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<FullUserProfileDto> getFullUserProfile(@PathVariable String username) {
        FullUserProfileDto userProfile = userService.getFullUserProfile(username);
        return ResponseEntity.ok(userProfile);
    }
}
