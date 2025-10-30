// Entity representing a user in the application.
package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean enabled;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Embedded
    private UserFlags flags = new UserFlags(); // Initialize to avoid nulls

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private MedicalProfile medicalProfile;

    @Column(name = "secret_user_key")
    private String verificationCode;
    private LocalDateTime verificationCodeExpires;

    @Column(unique = true)
    private String hashedAccessCode;

    // This is the unified relationship for both GUARDIAN and PROVIDER
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_patient_links", // Generic name for all links
        joinColumns = @JoinColumn(name = "user_id"), // Represents the guardian or provider
        inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<User> linkedPatients = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<GlucoseMeasurement> glucoseMeasurements = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<UserDevice> userDevices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserServiceConnection> serviceConnections = new HashSet<>();

    // Gets the user's ID.
    public Long getId() { return id; }
    // Sets the user's ID.
    public void setId(Long id) { this.id = id; }
    // Gets the username.
    public String getUsername() { return username; }
    // Sets the username.
    public void setUsername(String username) { this.username = username; }
    // Gets the email.
    public String getEmail() { return email; }
    // Sets the email.
    public void setEmail(String email) { this.email = email; }
    // Gets the password.
    public String getPassword() { return password; }
    // Sets the password.
    public void setPassword(String password) { this.password = password; }
    // Gets the first name.
    public String getFirstName() { return firstName; }
    // Sets the first name.
    public void setFirstName(String firstName) { this.firstName = firstName; }
    // Gets the last name.
    public String getLastName() { return lastName; }
    // Sets the last name.
    public void setLastName(String lastName) { this.lastName = lastName; }
    // Gets the date of birth.
    public LocalDate getDob() { return dob; }
    // Sets the date of birth.
    public void setDob(LocalDate dob) { this.dob = dob; }
    // Gets the user's role.
    public Role getRole() { return role; }
    // Sets the user's role.
    public void setRole(Role role) { this.role = role; }
    // Checks if the user is enabled.
    public boolean isEnabled() { return enabled; }
    // Sets the enabled status of the user.
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    // Gets the creation timestamp.
    public LocalDateTime getCreatedAt() { return createdAt; }
    // Sets the creation timestamp.
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    // Gets the user flags.
    public UserFlags getFlags() { return flags; }
    // Sets the user flags.
    public void setFlags(UserFlags flags) { this.flags = flags; }
    // Gets the user profile.
    public UserProfile getUserProfile() { return userProfile; }
    // Sets the user profile.
    public void setUserProfile(UserProfile userProfile) { this.userProfile = userProfile; }
    // Gets the verification code.
    public String getVerificationCode() { return verificationCode; }
    // Sets the verification code.
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
    // Gets the verification code expiration timestamp.
    public LocalDateTime getVerificationCodeExpires() { return verificationCodeExpires; }
    // Sets the verification code expiration timestamp.
    public void setVerificationCodeExpires(LocalDateTime verificationCodeExpires) { this.verificationCodeExpires = verificationCodeExpires; }
    // Gets the hashed access code.
    public String getHashedAccessCode() { return hashedAccessCode; }
    // Sets the hashed access code.
    public void setHashedAccessCode(String hashedAccessCode) { this.hashedAccessCode = hashedAccessCode; }
    // Gets the set of linked patients.
    public Set<User> getLinkedPatients() { return linkedPatients; }
    // Sets the set of linked patients.
    public void setLinkedPatients(Set<User> linkedPatients) { this.linkedPatients = linkedPatients; }
    // Gets the list of glucose measurements.
    public List<GlucoseMeasurement> getGlucoseMeasurements() { return glucoseMeasurements; }
    // Sets the list of glucose measurements.
    public void setGlucoseMeasurements(List<GlucoseMeasurement> glucoseMeasurements) { this.glucoseMeasurements = glucoseMeasurements; }
    // Gets the list of user devices.
    public List<UserDevice> getUserDevices() { return userDevices; }
    // Sets the list of user devices.
    public void setUserDevices(List<UserDevice> userDevices) { this.userDevices = userDevices; }
    // Gets the set of service connections.
    public Set<UserServiceConnection> getServiceConnections() { return serviceConnections; }
    // Sets the set of service connections.
    public void setServiceConnections(Set<UserServiceConnection> serviceConnections) { this.serviceConnections = serviceConnections; }

    // Gets the medical profile, if present.
    public Optional<MedicalProfile> getMedicalProfile() {
        return Optional.ofNullable(medicalProfile);
    }

    // Sets the medical profile.
    public void setMedicalProfile(MedicalProfile medicalProfile) {
        if (medicalProfile == null) {
            if (this.medicalProfile != null) {
                this.medicalProfile.setUser(null);
            }
        } else {
            medicalProfile.setUser(this);
        }
        this.medicalProfile = medicalProfile;
    }

    // Adds a glucose measurement to the user's list of measurements.
    public void addGlucoseMeasurement(GlucoseMeasurement measurement) {
        this.glucoseMeasurements.add(measurement);
        measurement.setUser(this);
    }

    // Compares this user to the specified object.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    // Returns a hash code value for the user.
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
