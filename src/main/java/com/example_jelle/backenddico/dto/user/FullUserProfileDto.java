package com.example_jelle.backenddico.dto.user;

import com.example_jelle.backenddico.model.enums.Gender;
import java.time.LocalDate;

public class FullUserProfileDto {

    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Gender gender; // Gebruikt nu de Enum
    private Double length;
    private Double weight;
    private String diabetesType;

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setLength(Double length) { this.length = length; }
    public void setWeight(Double weight) { this.weight = weight; }
    public void setDiabetesType(String diabetesType) { this.diabetesType = diabetesType; }
}
