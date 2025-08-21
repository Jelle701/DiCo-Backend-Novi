package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.payload.request.AuthenticationRequest;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.payload.request.VerifyRequest;
import com.example_jelle.backenddico.payload.response.AuthenticationResponse;
import com.example_jelle.backenddico.payload.response.MessageResponse;
import com.example_jelle.backenddico.service.UserService;
import com.example_jelle.backenddico.security.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        // FIXED: Convert username to lower case to ensure case-insensitivity
        String username = authenticationRequest.getUsername().toLowerCase();
        String password = authenticationRequest.getPassword();

        logger.info("Authentication attempt for username: {}", username);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for username: {}", username);
            throw new Exception("Incorrect username or password", e);
        }

        logger.info("Authentication successful for username: {}", username);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully! Please check your console for the verification code."));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody VerifyRequest verifyRequest) {
        userService.verifyUser(verifyRequest);
        return ResponseEntity.ok(new MessageResponse("User verified successfully! You can now log in."));
    }
}
