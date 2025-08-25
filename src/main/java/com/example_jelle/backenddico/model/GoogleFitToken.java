package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

/**
 * This entity stores the OAuth2 tokens (access and refresh) for the Google Fit API.
 * Each record is associated with a single user, allowing the application to make API calls on their behalf.
 */
@Entity
public class GoogleFitToken {

    /**
     * The unique identifier for the token record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user to whom these tokens belong.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * The short-lived access token used to authenticate API requests.
     * Stored as TEXT to accommodate its potential length.
     */
    @Column(columnDefinition = "TEXT")
    private String accessToken;

    /**
     * The long-lived refresh token used to obtain a new access token when the current one expires.
     * Stored as TEXT to accommodate its potential length.
     */
    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    public GoogleFitToken() {
    }

    public GoogleFitToken(User user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
