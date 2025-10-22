package com.example_jelle.backenddico.model;

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
    private List<UserDevice> userDevices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserServiceConnection> serviceConnections = new HashSet<>();

    // Getters and Setters

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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public UserFlags getFlags() { return flags; }
    public void setFlags(UserFlags flags) { this.flags = flags; }
    public UserProfile getUserProfile() { return userProfile; }
    public void setUserProfile(UserProfile userProfile) { this.userProfile = userProfile; }
    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
    public LocalDateTime getVerificationCodeExpires() { return verificationCodeExpires; }
    public void setVerificationCodeExpires(LocalDateTime verificationCodeExpires) { this.verificationCodeExpires = verificationCodeExpires; }
    public String getHashedAccessCode() { return hashedAccessCode; }
    public void setHashedAccessCode(String hashedAccessCode) { this.hashedAccessCode = hashedAccessCode; }
    public Set<User> getLinkedPatients() { return linkedPatients; }
    public void setLinkedPatients(Set<User> linkedPatients) { this.linkedPatients = linkedPatients; }
    public List<GlucoseMeasurement> getGlucoseMeasurements() { return glucoseMeasurements; }
    public void setGlucoseMeasurements(List<GlucoseMeasurement> glucoseMeasurements) { this.glucoseMeasurements = glucoseMeasurements; }
    public List<UserDevice> getUserDevices() { return userDevices; }
    public void setUserDevices(List<UserDevice> userDevices) { this.userDevices = userDevices; }
    public Set<UserServiceConnection> getServiceConnections() { return serviceConnections; }
    public void setServiceConnections(Set<UserServiceConnection> serviceConnections) { this.serviceConnections = serviceConnections; }

    public Optional<MedicalProfile> getMedicalProfile() {
        return Optional.ofNullable(medicalProfile);
    }

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

    public void addGlucoseMeasurement(GlucoseMeasurement measurement) {
        this.glucoseMeasurements.add(measurement);
        measurement.setUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
