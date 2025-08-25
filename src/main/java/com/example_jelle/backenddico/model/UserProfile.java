package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * This entity represents the detailed profile of a user, complementing the main User entity.
 * It holds personal information (name, DOB), physical attributes (gender, height, weight),
 * and medical details (diabetes type). It is linked to a User via a one-to-one relationship.
 */
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    /**
     * The unique identifier for the user profile.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double length;
    private Double weight;
    private String diabetesType;

    /**
     * A hashed secret key for authenticating API requests from external uploaders (e.g., Nightscout).
     */
    private String apiSecretHash;

    /**
     * The User account associated with this profile.
     * This relationship is lazy-loaded and ignored during JSON serialization to prevent infinite loops.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Explicitly define the foreign key column
    @JsonIgnore
    private User user;

    // Getters
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDob() { return dob; }
    public Gender getGender() { return gender; }
    public Double getLength() { return length; }
    public Double getWeight() { return weight; }
    public String getDiabetesType() { return diabetesType; }
    public String getApiSecretHash() { return apiSecretHash; }
    public User getUser() { return user; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setLength(Double length) { this.length = length; }
    public void setWeight(Double weight) { this.weight = weight; }
    public void setDiabetesType(String diabetesType) { this.diabetesType = diabetesType; }
    public void setApiSecretHash(String apiSecretHash) { this.apiSecretHash = apiSecretHash; }
    public void setUser(User user) { this.user = user; }
}
