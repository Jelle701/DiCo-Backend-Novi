package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String role;
    private boolean enabled;

    private String verificationCode;
    private LocalDateTime verificationCodeExpires;

    @Column(unique = true)
    private String hashedAccessCode; // Renamed from accessCode

    @Embedded
    private UserFlags flags = new UserFlags();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserDevice> devices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GlucoseMeasurement> glucoseMeasurements = new ArrayList<>();

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

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }

    public LocalDateTime getVerificationCodeExpires() { return verificationCodeExpires; }
    public void setVerificationCodeExpires(LocalDateTime verificationCodeExpires) { this.verificationCodeExpires = verificationCodeExpires; }

    public String getHashedAccessCode() { return hashedAccessCode; } // Renamed from getAccessCode
    public void setHashedAccessCode(String hashedAccessCode) { this.hashedAccessCode = hashedAccessCode; } // Renamed from setAccessCode

    public UserFlags getFlags() { return flags; }
    public void setFlags(UserFlags flags) { this.flags = flags; }

    public UserProfile getUserProfile() { return userProfile; }
    public void setUserProfile(UserProfile userProfile) { this.userProfile = userProfile; }

    public List<UserDevice> getDevices() { return devices; }
    public void setDevices(List<UserDevice> devices) { this.devices = devices; }

    public List<GlucoseMeasurement> getGlucoseMeasurements() {
        return glucoseMeasurements;
    }

    public void setGlucoseMeasurements(List<GlucoseMeasurement> glucoseMeasurements) {
        this.glucoseMeasurements = glucoseMeasurements;
    }

    public void addGlucoseMeasurement(GlucoseMeasurement measurement) {
        this.glucoseMeasurements.add(measurement);
        measurement.setUser(this);
    }
}
