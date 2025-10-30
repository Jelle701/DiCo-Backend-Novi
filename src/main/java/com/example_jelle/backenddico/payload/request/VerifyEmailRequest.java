// Data Transfer Object for email verification requests.
package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class VerifyEmailRequest {

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Verification code is required.")
    private String code;

    // Gets the user's email.
    public String getEmail() {
        return email;
    }

    // Sets the user's email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the verification code.
    public String getCode() {
        return code;
    }

    // Sets the verification code.
    public void setCode(String code) {
        this.code = code;
    }
}
