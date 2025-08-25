package com.example_jelle.backenddico;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main entry point for the Backend Dico Spring Boot application.
 * The @SpringBootApplication annotation enables auto-configuration, component scanning,
 * and other key Spring Boot features.
 */
@SpringBootApplication
public class BackendDicoApplication {

    /**
     * The main method that starts the application.
     * It first loads environment variables from a .env file using the Dotenv library.
     * These variables are then set as system properties before the Spring application is launched.
     * This allows for easy configuration of sensitive data like API keys and secrets without
     * hardcoding them into the source code.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Load the .env file
        Dotenv dotenv = Dotenv.load();

        // Set the properties as system properties BEFORE Spring Boot starts
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(BackendDicoApplication.class, args);
    }
}
