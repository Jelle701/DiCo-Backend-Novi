// Data Transfer Object for sending a response after successful authentication.
package com.example_jelle.backenddico.payload.response;

public class JwtResponse {
    // The JWT for authenticating subsequent requests.
    private String token;
    // The user's unique ID.
    private Long id;
    // The user's username.
    private String username;
    // The user's email address.
    private String email;
    // The user's role.
    private String role;
    // Flag indicating if the user has completed onboarding.
    private boolean onboardingCompleted;
    // An optional message for the client.
    private String message;

    // Constructs a new JwtResponse.
    public JwtResponse(String token, Long id, String username, String email, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.onboardingCompleted = true; // Default value
        this.message = null; // Default value
    }

    // Constructs a new JwtResponse with onboarding status and message.
    public JwtResponse(String token, Long id, String username, String email, String role, boolean onboardingCompleted, String message) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.onboardingCompleted = onboardingCompleted;
        this.message = message;
    }

    // Gets the JWT.
    public String getToken() { return token; }
    // Sets the JWT.
    public void setToken(String token) { this.token = token; }
    // Gets the user ID.
    public Long getId() { return id; }
    // Sets the user ID.
    public void setId(Long id) { this.id = id; }
    // Gets the username.
    public String getUsername() { return username; }
    // Sets the username.
    public void setUsername(String username) { this.username = username; }
    // Gets the email.
    public String getEmail() { return email; }
    // Sets the email.
    public void setEmail(String email) { this.email = email; }
    // Gets the user role.
    public String getRole() { return role; }
    // Sets the user role.
    public void setRole(String role) { this.role = role; }
    // Checks if onboarding is completed.
    public boolean isOnboardingCompleted() { return onboardingCompleted; }
    // Sets the onboarding completion status.
    public void setOnboardingCompleted(boolean onboardingCompleted) { this.onboardingCompleted = onboardingCompleted; }
    // Gets the message.
    public String getMessage() { return message; }
    // Sets the message.
    public void setMessage(String message) { this.message = message; }
}
