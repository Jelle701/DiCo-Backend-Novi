package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * This class is a Data Transfer Object (DTO) for email verification requests.
 * It encapsulates the user's email and the verification code they received.
 */
public class VerifyEmailRequest {

    /**
     * The email address to be verified.
     * It must not be blank and must be a valid email format.
     */
    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    /**
     * The verification code sent to the user's email.
     * It must not be blank.
     */
    @NotBlank(message = "Verification code is required.")
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
