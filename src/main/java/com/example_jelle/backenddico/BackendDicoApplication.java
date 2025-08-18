package com.example_jelle.backenddico;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendDicoApplication {
    public static void main(String[] args) {
        // Laad het .env bestand
        Dotenv dotenv = Dotenv.load();

        // Stel de properties in als system properties VOORDAT Spring Boot start
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(BackendDicoApplication.class, args);
    }
}
