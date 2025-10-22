package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.ServiceStatusDto;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import com.example_jelle.backenddico.security.CustomUserDetails;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the status of all external service connections for the currently authenticated user.
     * @param currentUser The details of the authenticated user.
     * @return A list of service statuses.
     */
    @GetMapping("/me/services")
    public ResponseEntity<List<ServiceStatusDto>> getMyServiceConnections(@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found in database."));

        List<ServiceStatusDto> statuses = userService.getServiceConnectionStatuses(user);
        return ResponseEntity.ok(statuses);
    }
}
