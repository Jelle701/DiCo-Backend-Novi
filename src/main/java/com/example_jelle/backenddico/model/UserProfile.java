package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.model.enums.DiabetesType;
import com.example_jelle.backenddico.model.enums.Gender;
import com.example_jelle.backenddico.model.enums.InsulinType;
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

    @Enumerated(EnumType.STRING)
    private InsulinType longActingInsulin;

    @Enumerated(EnumType.STRING)
    private InsulinType shortActingInsulin;

    private String apiSecretHash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public DiabetesType getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(DiabetesType diabetesType) {
        this.diabetesType = diabetesType;
    }

    public InsulinType getLongActingInsulin() {
        return longActingInsulin;
    }

    public void setLongActingInsulin(InsulinType longActingInsulin) {
        this.longActingInsulin = longActingInsulin;
    }

    public InsulinType getShortActingInsulin() {
        return shortActingInsulin;
    }

    public void setShortActingInsulin(InsulinType shortActingInsulin) {
        this.shortActingInsulin = shortActingInsulin;
    }

    public String getApiSecretHash() {
        return apiSecretHash;
    }

    public void setApiSecretHash(String apiSecretHash) {
        this.apiSecretHash = apiSecretHash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
