// Entity representing the detailed profile of a user.
package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.model.enums.DiabetesType;
import com.example_jelle.backenddico.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

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

    @Enumerated(EnumType.STRING)
    private DiabetesType diabetesType;

    private String longActingInsulin;

    private String shortActingInsulin;

    private String apiSecretHash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // Gets the user profile ID.
    public Long getId() {
        return id;
    }

    // Sets the user profile ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user's first name.
    public String getFirstName() {
        return firstName;
    }

    // Sets the user's first name.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Gets the user's last name.
    public String getLastName() {
        return lastName;
    }

    // Sets the user's last name.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Gets the user's date of birth.
    public LocalDate getDob() {
        return dob;
    }

    // Sets the user's date of birth.
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    // Gets the user's gender.
    public Gender getGender() {
        return gender;
    }

    // Sets the user's gender.
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    // Gets the user's length.
    public Double getLength() {
        return length;
    }

    // Sets the user's length.
    public void setLength(Double length) {
        this.length = length;
    }

    // Gets the user's weight.
    public Double getWeight() {
        return weight;
    }

    // Sets the user's weight.
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    // Gets the user's diabetes type.
    public DiabetesType getDiabetesType() {
        return diabetesType;
    }

    // Sets the user's diabetes type.
    public void setDiabetesType(DiabetesType diabetesType) {
        this.diabetesType = diabetesType;
    }

    // Gets the user's long-acting insulin.
    public String getLongActingInsulin() {
        return longActingInsulin;
    }

    // Sets the user's long-acting insulin.
    public void setLongActingInsulin(String longActingInsulin) {
        this.longActingInsulin = longActingInsulin;
    }

    // Gets the user's short-acting insulin.
    public String getShortActingInsulin() {
        return shortActingInsulin;
    }

    // Sets the user's short-acting insulin.
    public void setShortActingInsulin(String shortActingInsulin) {
        this.shortActingInsulin = shortActingInsulin;
    }

    // Gets the API secret hash.
    public String getApiSecretHash() {
        return apiSecretHash;
    }

    // Sets the API secret hash.
    public void setApiSecretHash(String apiSecretHash) {
        this.apiSecretHash = apiSecretHash;
    }

    // Gets the user associated with this profile.
    public User getUser() {
        return user;
    }

    // Sets the user associated with this profile.
    public void setUser(User user) {
        this.user = user;
    }
}
