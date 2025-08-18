package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.User;
// We importeren UserFlags niet meer direct in de DTO klasse zelf.
import java.time.LocalDate;

public class UserOutputDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;

    // FIX: Gebruik de UserFlagsDto in plaats van de UserFlags entiteit.
    // Dit ontkoppelt de API van het datamodel.
    private UserFlagsDto flags;

    // Static factory method voor veilige conversie van Entiteit naar DTO
    public static UserOutputDto from(User user) {
        UserOutputDto dto = new UserOutputDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setDob(user.getDob());

        // FIX: Roep de factory-methode van UserFlagsDto aan om de conversie te doen.
        if (user.getFlags() != null) {
            dto.setFlags(UserFlagsDto.from(user.getFlags()));
        }
        return dto;
    }

    // Getters en Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    // FIX: Getter en Setter gebruiken nu UserFlagsDto
    public UserFlagsDto getFlags() { return flags; }
    public void setFlags(UserFlagsDto flags) { this.flags = flags; }
}