// Entity storing OAuth2 tokens for the Google Fit API.
package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

@Entity
public class GoogleFitToken {

    // The unique identifier for the token record.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user to whom these tokens belong.
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // The short-lived access token.
    @Column(columnDefinition = "TEXT")
    private String accessToken;

    // The long-lived refresh token.
    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    // Default constructor.
    public GoogleFitToken() {
    }

    // Constructs a new GoogleFitToken.
    public GoogleFitToken(User user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Gets the ID of the token record.
    public Long getId() {
        return id;
    }

    // Sets the ID of the token record.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user associated with these tokens.
    public User getUser() {
        return user;
    }

    // Sets the user associated with these tokens.
    public void setUser(User user) {
        this.user = user;
    }

    // Gets the access token.
    public String getAccessToken() {
        return accessToken;
    }

    // Sets the access token.
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    // Gets the refresh token.
    public String getRefreshToken() {
        return refreshToken;
    }

    // Sets the refresh token.
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
