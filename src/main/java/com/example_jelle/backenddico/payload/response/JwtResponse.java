// Bestandslocatie: src/main/java/com/example_jelle/backenddico/payload/response/JwtResponse.java
package com.example_jelle.backenddico.payload.response;

public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean onboardingCompleted;
    private String message;

    public JwtResponse(String token, Long id, String username, String email, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.onboardingCompleted = true; // Standaardwaarde
        this.message = null; // Standaardwaarde
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

    // Getters en Setters
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
