package com.example_jelle.backenddico.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LibreViewCredentialsRequest {

    @NotBlank
    @Email
    private String libreViewEmail;

    @NotBlank
    private String libreViewPassword;

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
