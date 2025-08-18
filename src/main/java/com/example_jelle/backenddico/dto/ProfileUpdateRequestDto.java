package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.enums.Gender;
import java.time.LocalDate;

/**
 * DTO voor het updaten van de profielgegevens van een gebruiker.
 * Deze klasse vangt de data op die via de API binnenkomt.
 */
public class ProfileUpdateRequestDto {

    // Basisgegevens
    private String firstName;
    private String lastName;
    private LocalDate dob;

    // Voorkeuren
    private String measurementSystem; // "metric" of "imperial"
    private Gender gender;
    private Double weight;
    private Double height;

    // Medische informatie
    private String diabetesType;
    private String longActingInsulin;
    private String shortActingInsulin;

    // --- GETTERS EN SETTERS ---
    // IntelliJ kan deze automatisch voor je genereren (Alt + Insert -> Getter and Setter)

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getMeasurementSystem() { return measurementSystem; }
    public void setMeasurementSystem(String measurementSystem) { this.measurementSystem = measurementSystem; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public String getDiabetesType() { return diabetesType; }
    public void setDiabetesType(String diabetesType) { this.diabetesType = diabetesType; }

    public String getLongActingInsulin() { return longActingInsulin; }
    public void setLongActingInsulin(String longActingInsulin) { this.longActingInsulin = longActingInsulin; }

    public String getShortActingInsulin() { return shortActingInsulin; }
    public void setShortActingInsulin(String shortActingInsulin) { this.shortActingInsulin = shortActingInsulin; }
}