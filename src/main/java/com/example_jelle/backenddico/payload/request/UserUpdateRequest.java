// Data Transfer Object for updating user information.
package com.example_jelle.backenddico.payload.request;

public class UserUpdateRequest {

    private String libreViewEmail;
    private String libreViewPassword;

    // Gets the LibreView email.
    public String getLibreViewEmail() {
        return libreViewEmail;
    }

    // Sets the LibreView email.
    public void setLibreViewEmail(String libreViewEmail) {
        this.libreViewEmail = libreViewEmail;
    }

    // Gets the LibreView password.
    public String getLibreViewPassword() {
        return libreViewPassword;
    }

    // Sets the LibreView password.
    public void setLibreViewPassword(String libreViewPassword) {
        this.libreViewPassword = libreViewPassword;
    }
}
