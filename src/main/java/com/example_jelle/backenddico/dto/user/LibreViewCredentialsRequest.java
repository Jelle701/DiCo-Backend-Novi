// Request model for submitting LibreView credentials.
package com.example_jelle.backenddico.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LibreViewCredentialsRequest {

    @NotBlank
    @Email
    private String libreViewEmail;

    @NotBlank
    private String libreViewPassword;

    // Gets the user's LibreView email.
    public String getLibreViewEmail() {
        return libreViewEmail;
    }

    // Sets the user's LibreView email.
    public void setLibreViewEmail(String libreViewEmail) {
        this.libreViewEmail = libreViewEmail;
    }

    // Gets the user's LibreView password.
    public String getLibreViewPassword() {
        return libreViewPassword;
    }

    // Sets the user's LibreView password.
    public void setLibreViewPassword(String libreViewPassword) {
        this.libreViewPassword = libreViewPassword;
    }
}
