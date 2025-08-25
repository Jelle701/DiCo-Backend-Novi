package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a user in the application. This is the central entity for authentication and user data.
 * It stores credentials, personal information, role, and relationships with other users (patients, guardians, providers)
 * and their associated health data.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user's unique username, used for login.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * The user's unique email address, also used for login and communication.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The user's hashed password for authentication.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's date of birth.
     */
    private LocalDate dob;

    /**
     * The user's role in the system (e.g., PATIENT, GUARDIAN, PROVIDER).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Flag indicating if the user's account is enabled. Disabled accounts cannot log in.
     */
    private boolean enabled;

    /**
     * A temporary code sent to the user for account verification.
     */
    @Column(name = "secret_user_key")
    private String verificationCode;

    /**
     * The expiration timestamp for the verification code.
     */
    private LocalDateTime verificationCodeExpires;

    /**
     * A hashed version of the access code for linking accounts.
     */
    @Column(unique = true)
    private String hashedAccessCode;

    /**
     * For a GUARDIAN, this links to the single PATIENT they are monitoring.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_patient_id")
    private User linkedPatient;

    /**
     * For a PROVIDER, this links to the set of PATIENTs they are monitoring.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "provider_patient_links",
        joinColumns = @JoinColumn(name = "provider_id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<User> linkedPatients = new HashSet<>();

    /**
     * A list of all glucose measurements recorded by this user.
     * The relationship is managed with CascadeType.ALL, so measurements are saved/deleted with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GlucoseMeasurement> glucoseMeasurements = new ArrayList<>();

    // Getters and Setters ...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
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
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
    public LocalDateTime getVerificationCodeExpires() { return verificationCodeExpires; }
    public void setVerificationCodeExpires(LocalDateTime verificationCodeExpires) { this.verificationCodeExpires = verificationCodeExpires; }
    public String getHashedAccessCode() { return hashedAccessCode; }
    public void setHashedAccessCode(String hashedAccessCode) { this.hashedAccessCode = hashedAccessCode; }
    public User getLinkedPatient() { return linkedPatient; }
    public void setLinkedPatient(User linkedPatient) { this.linkedPatient = linkedPatient; }
    public Set<User> getLinkedPatients() { return linkedPatients; }
    public void setLinkedPatients(Set<User> linkedPatients) { this.linkedPatients = linkedPatients; }
    public List<GlucoseMeasurement> getGlucoseMeasurements() { return glucoseMeasurements; }
    public void setGlucoseMeasurements(List<GlucoseMeasurement> glucoseMeasurements) { this.glucoseMeasurements = glucoseMeasurements; }

    /**
     * Helper method to add a glucose measurement to the user.
     * This ensures that the bidirectional relationship between User and GlucoseMeasurement is correctly maintained.
     * @param measurement The GlucoseMeasurement to add.
     */
    public void addGlucoseMeasurement(GlucoseMeasurement measurement) {
        this.glucoseMeasurements.add(measurement);
        measurement.setUser(this);
    }
}
