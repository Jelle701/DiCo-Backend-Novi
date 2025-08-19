package com.example_jelle.backenddico.model;

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
    private String diabetesType;

    private String apiSecretHash;

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
