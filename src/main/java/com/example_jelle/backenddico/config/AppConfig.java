package com.example_jelle.backenddico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This class provides application-wide bean definitions.
 * It is used to configure components that can be shared across the application.
 */
@Configuration
public class AppConfig {

    /**
     * Creates and configures a RestTemplate bean.
     * RestTemplate is a synchronous client to perform HTTP requests, and making it a bean
     * allows it to be easily injected into other services (like GoogleFitServiceImpl)
     * that need to communicate with external APIs.
     * @return A new RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
