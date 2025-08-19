package com.example_jelle.backenddico.payload.request;

// Gebruik Lombok voor schone code, of schrijf de getters handmatig als Lombok niet werkt.
import lombok.Getter;

@Getter
public class RegisterRequest {
    private String username;
    private String password;
}
