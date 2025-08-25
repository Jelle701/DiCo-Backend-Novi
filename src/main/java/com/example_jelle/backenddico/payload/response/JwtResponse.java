package com.example_jelle.backenddico.payload.response;

/**
 * This class is a Data Transfer Object (DTO) for sending a response after successful authentication.
 * It contains the JSON Web Token (JWT) along with essential user details and status information.
 */
public class JwtResponse {
    /**
     * The JWT that the client should use for authenticating subsequent requests.
     */
    private String token;
    /**
     * The unique ID of the authenticated user.
     */
    private Long id;
    /**
     * The username of the authenticated user.
     */
    private String username;
    /**
     * The email address of the authenticated user.
     */
    private String email;
    /**
     * The role of the authenticated user (e.g., "PATIENT", "PROVIDER").
     */
    private String role;
    /**
     * A flag indicating whether the user has completed the onboarding process.
     */
    private boolean onboardingCompleted;
    /**
     * An optional message to be sent to the client.
     */
    private String message;

    public JwtResponse(String token, Long id, String username, String email, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.onboardingCompleted = true; // Default value
        this.message = null; // Default value
    }

    public JwtResponse(String token, Long id, String username, String email, String role, boolean onboardingCompleted, String message) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.onboardingCompleted = onboardingCompleted;
        this.message = message;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isOnboardingCompleted() { return onboardingCompleted; }
    public void setOnboardingCompleted(boolean onboardingCompleted) { this.onboardingCompleted = onboardingCompleted; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
