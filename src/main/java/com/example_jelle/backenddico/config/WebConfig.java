package com.example_jelle.backenddico.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class provides web-specific configuration for the application, implementing WebMvcConfigurer
 * to customize settings like Cross-Origin Resource Sharing (CORS).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures the application's CORS policy.
     * This setup is for local development and allows the frontend application running on http://localhost:5173
     * to make requests to the backend API endpoints (under /api/**).
     * @param registry The CORS registry to which the configuration is added.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Adjust this if your API path is different
                .allowedOrigins("http://localhost:5173") // The exact URL of your frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed methods
                .allowedHeaders("*") // Allowed headers
                .allowCredentials(true); // Allow credentials
    }
}

// Example for a remote/production environment with more open access:
// registry.addMapping("/**")
//         .allowedOrigins("*") // Allows all domains
//         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//         .allowCredentials(false); // Credentials cannot be used with a wildcard "*" origin.
