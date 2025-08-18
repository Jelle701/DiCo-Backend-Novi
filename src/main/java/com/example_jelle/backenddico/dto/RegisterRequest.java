// Bestandslocatie: src/main/java/com/example_jelle/backenddico/payload/request/RegisterRequest.java
package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // <-- Importeer @NotNull
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class RegisterRequest {

    @NotBlank(message = "Email is verplicht.")
    @Email(message = "Voer een geldig e-mailadres in.")
    private String email;

    @NotBlank(message = "Wachtwoord is verplicht.")
    @Size(min = 6, message = "Wachtwoord moet minimaal 6 tekens lang zijn.")
    private String password;

    @NotBlank(message = "Voornaam is verplicht.")
    private String firstName;

    @NotBlank(message = "Achternaam is verplicht.")
    private String lastName;

    // FIX: Voeg @NotNull toe om te valideren dat de geboortedatum wordt meegegeven.
    // Dit voorkomt de databasefout en geeft een duidelijke 400 Bad Request terug.
    @NotNull(message = "Geboortedatum is verplicht.")
    private LocalDate dob; // Geboortedatum

    // --- GETTERS EN SETTERS ---

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
}