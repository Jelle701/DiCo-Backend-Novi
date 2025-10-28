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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
        String username = authenticationRequest.getUsername().toLowerCase();
        String password = authenticationRequest.getPassword();

        logger.info("Received authentication request for username: {}", username);
        logger.debug("Password for {}: {}", username, password); // Added for debugging, be careful in production

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for username: {}. Reason: {}", username, e.getMessage());
            // Return 401 Unauthorized with a generic message to prevent user enumeration
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Ongeldige gebruikersnaam of wachtwoord."));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during authentication for username: {}.", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Er is een interne serverfout opgetreden."));
        }

        logger.info("Authentication successful for username: {}", username);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse response = new AuthenticationResponse(jwt);
        logger.info("Returning JWT token for user: {}", username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        logger.info("Received registration request for email: {}", registerRequest.getEmail());
        userService.register(registerRequest);
        MessageResponse response = new MessageResponse("User registered successfully! Please check your console for the verification code.");
        logger.info("User registration successful for email: {}.", registerRequest.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody VerifyRequest verifyRequest) {
        logger.info("Received verification request with token: {}", verifyRequest.getToken());
        userService.verifyUser(verifyRequest);
        MessageResponse response = new MessageResponse("User verified successfully! You can now log in.");
        logger.info("User verification successful for token: {}", verifyRequest.getToken());
        return ResponseEntity.ok(response);
    }
}
