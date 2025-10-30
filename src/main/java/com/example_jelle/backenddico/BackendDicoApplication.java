// Main application class for the Backend Dico project.
package com.example_jelle.backenddico;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendDicoApplication {

    // Main method to run the Spring Boot application.
    public static void main(String[] args) {
        // Load the .env file from the project root
        Dotenv dotenv = Dotenv.load();

        // Set the properties from .env as system properties
        // Spring Boot will automatically pick them up to resolve placeholders like ${DB_USERNAME}
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(BackendDicoApplication.class, args);
    }
}
