package com.example_jelle.backenddico.dto.onboarding;

import com.example_jelle.backenddico.model.enums.Gender;
import java.time.LocalDate;

public class OnboardingRequestDto {

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Gender gender; // Gebruikt nu de Enum
    private Double length;
    private Double weight;
    private String diabetesType;

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDob() { return dob; }
    public Gender getGender() { return gender; }
    public Double getLength() { return length; }
    public Double getWeight() { return weight; }
    public String getDiabetesType() { return diabetesType; }
}
