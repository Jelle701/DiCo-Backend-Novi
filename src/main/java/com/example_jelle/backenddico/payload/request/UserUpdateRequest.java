package com.example_jelle.backenddico.payload.request;

public class UserUpdateRequest {

    private String libreViewEmail;
    private String libreViewPassword;

    // Getters and Setters
    public String getLibreViewEmail() {
        return libreViewEmail;
    }

    public void setLibreViewEmail(String libreViewEmail) {
        this.libreViewEmail = libreViewEmail;
    }

    public String getLibreViewPassword() {
        return libreViewPassword;
    }

    public void setLibreViewPassword(String libreViewPassword) {
        this.libreViewPassword = libreViewPassword;
    }
}
